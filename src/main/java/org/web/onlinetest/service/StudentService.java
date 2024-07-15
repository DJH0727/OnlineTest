package org.web.onlinetest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.web.onlinetest.main.Course;
import org.web.onlinetest.main.Exam;
import org.web.onlinetest.main.Question;
import org.web.onlinetest.main.QusOption;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Transactional
public class StudentService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    QuestionService questionService;


    RowMapper<Question> questionRowMapper = new BeanPropertyRowMapper<>(Question.class);
    RowMapper<QusOption> answerRowMapper = new BeanPropertyRowMapper<>(QusOption.class);
    RowMapper<Course> courseRowMapper = new BeanPropertyRowMapper<>(Course.class);
    RowMapper<Exam> examRowMapper = new BeanPropertyRowMapper<>(Exam.class);

    public List<Question> findQuestionsByCourseId(Integer courseId1, Integer courseId2) {
        List<Question> questions = null;
        List<QusOption> options = null;
        try {
            questions = jdbcTemplate.query("select * from questions where cid=? or cid=?", questionRowMapper, courseId1, courseId2);
            for (Question question : questions) {
                String courseName = jdbcTemplate.queryForObject("select cname from courses where cid=?", String.class, question.getCid());
                question.setCourseName(courseName);
                options = jdbcTemplate.query("select * from options where qid=?", answerRowMapper, question.getQid());
                question.setOptions(options);
            }
        }
        catch (Exception e) {
            logger.error("findAllQuestions error", e);
        }
        return questions;
    }

    //根据题目类型获取题目
    public List<Question> getTypeQuestions(List<Question> questions, Integer typeId) {
        List<Question> resultQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.getQtype() == typeId) {
                resultQuestions.add(question);
            }
        }
        return resultQuestions;
    }

    //随机抽取题目
    public static List<Question> getRandomQuestions(List<Question> questions, int numberOfQuestions) {
        List<Question> selectedQuestions = new ArrayList<>();
        Random random = new Random();
        List<Integer> selectedIndices = new ArrayList<>();

        while (selectedQuestions.size() < numberOfQuestions&&selectedIndices.size()<questions.size()) {
            int randomIndex = random.nextInt(questions.size());
            if (!selectedIndices.contains(randomIndex)) {
                selectedQuestions.add(questions.get(randomIndex));
                selectedIndices.add(randomIndex);
            }
        }

        return selectedQuestions;
    }


    //创建试卷
    public int createPaper(List<Question> questions) {
        String sql = "SELECT Max(pid) FROM paper";
        Integer maxPid = jdbcTemplate.queryForObject(sql, Integer.class);
        if (maxPid==null) { maxPid=0;}
        int pid = maxPid+1;
        for (Question question : questions) {
            try {
                jdbcTemplate.update("INSERT INTO paper(pid, qid) VALUES (?,?)", pid, question.getQid());
            } catch (Exception e) {
                logger.error("createPaper error", e);
                return -1;
            }
        }
        logger.info("createPaper success");
        return pid;


    }





    public void createExam(String uid,Integer courseId1, Integer courseId2, Integer singleChoice,
                           Integer multipleChoice, Integer trueFalse, Integer duration, String examName) {
        List<Question> questions = findQuestionsByCourseId(courseId1, courseId2);

        //分类并随机抽取题目
        List<Question> singleChoiceQuestions =getRandomQuestions(getTypeQuestions(questions, 1), singleChoice) ;
        List<Question> multipleChoiceQuestions = getRandomQuestions(getTypeQuestions(questions, 2), multipleChoice);
        List<Question> trueFalseQuestions = getRandomQuestions(getTypeQuestions(questions, 3), trueFalse);

        //合并题目
        List<Question> allQuestions = new ArrayList<>();
        allQuestions.addAll(trueFalseQuestions);
        allQuestions.addAll(singleChoiceQuestions);
        allQuestions.addAll(multipleChoiceQuestions);

        int pid = createPaper(allQuestions);
        if (pid==-1) {
            logger.error("createExam error");
            return;
        }
        try {
            LocalDate examDate = LocalDate.now(); // 获取当前日期
            Date sqlDate = Date.valueOf(examDate);
            jdbcTemplate.update("INSERT INTO exams(ename, pid, uid, edate, etime, totalscore,cid1,cid2) VALUES (?,?,?,?,?,?,?,?)",
                    examName,pid,uid,sqlDate,duration,allQuestions.size(),courseId1,courseId2);
        } catch (Exception e) {
            logger.error("createExam error", e);
            return;
        }
        logger.info("createExam success");
    }


    //获取我的考试 0:未开始 1:进行中 2:已结束
    public List<Exam> getMyExam(String uid,int status) {
        String sql = "SELECT * FROM exams WHERE uid=? AND status=?";
        List<Exam> exams = null;
        try {
            exams = jdbcTemplate.query(sql, examRowMapper, uid, status);
            for (Exam exam : exams) {
                String courseName1 = jdbcTemplate.queryForObject("SELECT cname FROM courses WHERE cid=?", String.class, exam.getCid1());
                String courseName2 = jdbcTemplate.queryForObject("SELECT cname FROM courses WHERE cid=?", String.class, exam.getCid2());
                exam.setCourseName1(courseName1);
                exam.setCourseName2(courseName2);
            }
        }
        catch (Exception e) {
            logger.error("getMyExam error", e);
        }
        return exams;
    }

    public Exam getExamByEid(Integer eid) {
        String sql = "SELECT * FROM exams WHERE eid=?";
        Exam exam = null;
        try {
            exam = jdbcTemplate.queryForObject(sql, examRowMapper, eid);
            if (exam==null) { return null; }
            String courseName1 = jdbcTemplate.queryForObject("SELECT cname FROM courses WHERE cid=?", String.class, exam.getCid1());
            String courseName2 = jdbcTemplate.queryForObject("SELECT cname FROM courses WHERE cid=?", String.class, exam.getCid2());
            exam.setCourseName1(courseName1);
            exam.setCourseName2(courseName2);
        }
        catch (Exception e) {
            logger.error("getExamByEid error", e);
        }
        return exam;

    }

    public List<Question> getQuestionsByEid(Integer eid) {
        String sql = "SELECT qid FROM paper WHERE pid IN (SELECT pid FROM exams WHERE eid=?)";
        List<Integer> qids = new ArrayList<>();
        try {
            qids = jdbcTemplate.queryForList(sql, Integer.class, eid);
        }
        catch (Exception e) {
            logger.error("getQuestionsByEid error", e);
        }
        System.out.println("qids="+qids);
        List<Question> questions = new ArrayList<>();
        List<QusOption> options;
        Question question = null;
        for (Integer qid : qids) {
            try {
                question = jdbcTemplate.queryForObject("select * from questions where qid=?", questionRowMapper, qid);
                if (question==null) { return null; }
                String courseName = jdbcTemplate.queryForObject("select cname from courses where cid=?", String.class, question.getCid());
                question.setCourseName(courseName);
                options = jdbcTemplate.query("select * from options where qid=?", answerRowMapper, question.getQid());
                question.setOptions(options);
            }
            catch (Exception e) {
                logger.error("getQuestionsByEid error", e);
            }
            if (question==null) { continue; }
            questions.add(question);
        }

        return questions;
    }


}
