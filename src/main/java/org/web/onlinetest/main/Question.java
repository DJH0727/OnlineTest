package org.web.onlinetest.main;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.w3c.dom.Text;
import org.web.onlinetest.service.QuestionService;
import org.web.onlinetest.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class Question {



    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getQtype() {
        return qtype;
    }

    public void setQtype(int qtype) {
        if(qtype==1) typeName="单选";
        else if(qtype==2) typeName="多选";
        else if(qtype==3) typeName="判断";
        this.qtype = qtype;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getQtext() {
        return qtext;
    }

    public void setQtext(String qtext) {
        this.qtext = qtext;
    }

    public String getQurl() {
        return qurl;
    }

    public void setQurl(String qurl) {
        this.qurl = qurl;
    }

    public int getQscore() {
        return qscore;
    }

    public void setQscore(int qscore) {
        this.qscore = qscore;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<QusOption> getOptions() {
        return options;
    }

    public void setOptions(List<QusOption> options) {
        this.options = options;
    }
    private int qid;// 问题id
    private int cid;// 课程id
    private int qtype;// 问题类型 1单选 2多选 3判断
    private String qtext;// 问题内容
    private String qurl;// 问题附件地址
    private int qscore;// 问题分值
    private String answer;// 答案
    private List<QusOption> options;// 选项列表

    private String typeName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    private String courseName;

    public Question() {}
    public String toString() {
        return "Question [qid=" + qid + ", cid=" + cid + ", qtype=" + qtype + ", qtext=" + qtext + ", qurl=" + qurl + ", qscore=" + qscore + ", answer=" + answer + ", options=" + options + "]";
    }
    public String getOptionA() {
        if(options.size()>=2)
            return options.get(0).getOptext();
        else return null;
    }
    public String getOptionB() {
        if(options.size()>=2)
            return options.get(1).getOptext();
        else return null;
    }
    public String getOptionC() {
        if(options.size()>=4)
            return options.get(2).getOptext();
        else return null;
    }
    public String getOptionD() {
        if(options.size()>=4)
            return options.get(3).getOptext();
        else return null;
    }


}