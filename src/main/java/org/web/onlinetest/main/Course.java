package org.web.onlinetest.main;

public class Course {
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public boolean isIs_major() {
        return is_major;
    }

    public void setIs_major(boolean is_major) {
        this.is_major = is_major;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    private int cid;
    private String cname;
    private boolean is_major;
    public Course() {}
    public String toString() {
        return "Course [cid=" + cid + ", cname=" + cname + ", is_major=" + is_major + "]";
    }
}
