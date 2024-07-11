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



    public List<Question> findQuestionsByCidAndQtype(List<Question> questions,int courseId, int questionType) {
            List<Question> result = new ArrayList<>();
            if (courseId == -1 && questionType == -1) {
                return questions;
            }
            if (questionType == -1) {
                for (Question question : questions) {
                    if (question.getCid() == courseId) {
                        result.add(question);
                    }
                }
            }
            else if(courseId == -1) {
                for (Question question : questions) {
                    if (question.getQtype() == questionType) {
                        result.add(question);
                    }
                }
            }
            else {
                for (Question question : questions) {
                    if (question.getCid() == courseId && question.getQtype() == questionType) {
                        result.add(question);
                    }
                }
            }
            return result;
    }


    public List<Question> searchQuestionByKeyword(String keyword) {
        List<Question> questions = null;
        List<QusOption> options = null;
        try {
            questions = jdbcTemplate.query("select * from questions where qtext like ?", questionRowMapper, "%" + keyword + "%");
            for (Question question : questions) {
                String courseName = jdbcTemplate.queryForObject("select cname from courses where cid=?", String.class, question.getCid());
                question.setCourseName(courseName);
                options = jdbcTemplate.query("select * from options where qid=?", answerRowMapper, question.getQid());
                question.setOptions(options);
            }
        }
        catch (Exception e) {
            logger.error("searchQuestionByKeyword error", e);
        }
        return questions;
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
}
