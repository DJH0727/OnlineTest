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
import org.web.onlinetest.main.Question;
import org.web.onlinetest.main.QusOption;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Component
@Transactional
public class QuestionService {

    //日志记录
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JdbcTemplate jdbcTemplate;
    RowMapper<Question> questionRowMapper = new BeanPropertyRowMapper<>(Question.class);
    RowMapper<QusOption> answerRowMapper = new BeanPropertyRowMapper<>(QusOption.class);
    RowMapper<Course> courseRowMapper = new BeanPropertyRowMapper<>(Course.class);

    public int getQuestionCount() {
        String sql = "select MAX(qid) from questions";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    public int geCidByName(String cname) {
        String sql = "select * from courses where cname=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cname);
    }
    public List<Course> getAllCourses() {
        String sql = "select * from courses";
        return jdbcTemplate.query(sql, courseRowMapper);
    }



    public boolean addQuestion(Question question) {
        String sql = "insert into questions(qid,cid, qtype, qtext, qurl, qscore, answer) values(?,?,?,?,?,?,?)";
        int qidCount =getQuestionCount()+1;
        try {
            jdbcTemplate.update(sql,
                    qidCount, question.getCid(), question.getQtype(), question.getQtext(), question.getQurl(), question.getQscore(), question.getAnswer());
            List<QusOption> options = question.getOptions();
            for (QusOption option : options) {
                jdbcTemplate.update("INSERT  INTO options(qid, op, optext, opurl) VALUES(?,?,?,?)",
                            qidCount, option.getOp(), option.getOptext(), option.getOpurl());
            }
        }
        catch (Exception e) {
            logger.error("addQuestion error", e);
            return false;
        }
        logger.info("addQuestion success");
        return true;
    }

    public List<Question> findAllQuestions() {
        List<Question> questions = null;
        List<QusOption> options = null;
        try {
             questions = jdbcTemplate.query("select * from questions", questionRowMapper);
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


    public List<Question> searchQuestionByKeyword(String keyword,int cid,int qtype) {
        List<Question> questions = findAllQuestions();
        List<Question> select = new ArrayList<>();
        for (Question question : questions) {

            if(cid==-1&&qtype==-1)
            {
                select.add(question);
            }
            else if(cid!=-1&&qtype==-1)
            {
                if(question.getCid() == cid) select.add(question);
            }
            else if(cid==-1&&qtype!=-1)
            {
                if(question.getQtype()==qtype) select.add(question);
            }
            else if(cid != -1 && qtype != -1)
            {
                if(question.getCid() == cid && question.getQtype() == qtype) select.add(question);
            }
        }

        List<Question> result = new ArrayList<>();
        for (Question question : select) {
            Pattern pattern = Pattern.compile(keyword);
            if(pattern.matcher(question.getQtext()).find()) result.add(question);
        }
        return result;
    }

    public boolean deleteQuestion(int qid) {
        String sql = "delete from questions where qid=?";
        try {
            jdbcTemplate.update("delete from options where qid=?", qid);
            jdbcTemplate.update(sql, qid);
        }
        catch (Exception e) {
            logger.error("deleteQuestion error", e);
            return false;
        }
        logger.info("deleteQuestion success");
        return true;
    }

    public boolean updateQuestion(int qid, String question,
                                  String optionA, String optionB, String optionC, String optionD,
                                  String answer,int cid) {

       try {
           jdbcTemplate.update("update questions set qtext=?, cid=?,answer=? where qid=?", question, cid, answer, qid);
           jdbcTemplate.update("update options set optext=? where qid=? and op=?", optionA, qid, 1);
           jdbcTemplate.update("update options set optext=? where qid=? and op=?", optionB, qid, 2);
           jdbcTemplate.update("update options set optext=? where qid=? and op=?", optionC, qid, 3);
           jdbcTemplate.update("update options set optext=? where qid=? and op=?", optionD, qid, 4);
       }
       catch (Exception e) {
           logger.error("updateQuestion error", e);
           return false;
       }
        return true;
    }
    public boolean updateQuestion(int qid, String question,String answer,int cid) {
        try {
            jdbcTemplate.update("update questions set qtext=?, cid=?,answer=? where qid=?", question, cid, answer, qid);
        }
        catch (Exception e) {
            logger.error("updateQuestion error", e);
            return false;
        }
        return true;
    }

}
