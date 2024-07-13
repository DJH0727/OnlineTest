package org.web.onlinetest.main;

public class User {

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }
    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    private String uno;//编号
    private String uid;
    private int role;// 0: admin, 1: student;
    private String pwd;
    private String name;
    private String email;
    private String phone;
    private String imgurl;

    public User() {
    }

    public User(String uid, int role, String pwd, String name, String email, String phone, String imgurl) {
        this.uid = uid;
        this.role = role;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imgurl = imgurl;

    }
    public String toString() {
        return "User [uid=" + uid + ", role=" + role + ", pwd=" + pwd + ", name=" + name + ", email=" + email + ", phone=" + phone + ", imgurl=" + imgurl + "]";
    }

}
