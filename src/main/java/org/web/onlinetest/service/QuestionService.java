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
        String sql = "select count(*) from questions";
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

}
