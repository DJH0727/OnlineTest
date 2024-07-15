package org.web.onlinetest.main;

public class paper {

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getUseranswer() {
        return useranswer;
    }

    public void setUseranswer(String useranswer) {
        this.useranswer = useranswer;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    private int pid;
    int qid;
    String useranswer;



    public String toString() {
        return "pid: " + pid + " qid: " + qid + " useranswer: " + useranswer;
    }
}
