package org.web.onlinetest.main;

public class QusOption {


    public int getOpid() {
        return opid;
    }

    public void setOpid(int opid) {
        this.opid = opid;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public String getOptext() {
        return optext;
    }

    public void setOptext(String optext) {
        this.optext = optext;
    }

    public String getOpurl() {
        return opurl;
    }

    public void setOpurl(String opurl) {
        this.opurl = opurl;
    }

    private int opid;// option id
    private int qid;// question id
    private int op;// option 1:A, 2:B, 3:C, 4:D
    private String optext;// option text
    private String opurl;// option url


    public QusOption(){}

    public String toString(){
        return "QusOption [opid=" + opid + ", qid=" + qid + ", op=" + op + ", optext=" + optext + ", opurl=" + opurl + "]";
    }

}
