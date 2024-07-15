package org.web.onlinetest.main;

import java.sql.Date;

public class Exam {


    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public Integer getEtime() {
        return etime;
    }

    public void setEtime(Integer etime) {
        this.etime = etime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(Integer totalscore) {
        this.totalscore = totalscore;
    }

    public Integer getUserscore() {
        return userscore;
    }

    public void setUserscore(Integer userscore) {
        this.userscore = userscore;
    }

    public String getCourseName1() {
        return CourseName1;
    }

    public void setCourseName1(String courseName1) {
        CourseName1 = courseName1;
    }

    public String getCourseName2() {
        return CourseName2;
    }

    public void setCourseName2(String courseName2) {
        CourseName2 = courseName2;
    }

    public Integer getCid2() {
        return cid2;
    }

    public void setCid2(Integer cid2) {
        this.cid2 = cid2;
    }

    public Integer getCid1() {
        return cid1;
    }

    public void setCid1(Integer cid1) {
        this.cid1 = cid1;
    }
    Integer eid;
    String ename;
    Integer pid;
    Date  edate;
    Integer etime;//持续时间 min
    Integer status;//考试状态 0-未开始 1-进行中 2-已结束
    Integer totalscore;
    Integer userscore;
    Integer cid1;
    Integer cid2;

    String CourseName1;
    String CourseName2;

    public String toString(){
        return "Exam [eid=" + eid + ", enamel=" + ename + ", " +
                "pid=" + pid + ", edate=" + edate + ", etime=" + etime + ", status=" + status + ", totalscore=" + totalscore + ", " +
                "userscore=" + userscore + ", CourseName1=" + CourseName1 + ", CourseName2=" + CourseName2 + "]";
    }
}
