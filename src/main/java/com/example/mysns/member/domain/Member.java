package com.example.mysns.member.domain;


public class Member {

    private int id;
    private String profileImg;
    private String email;
    private String name;
    private String nick;
    private String pwd;
    private String address;

    private boolean followed;

    //프로필변경
    private String obj;
    private String oldObj;
    private String newObj;


    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getOldObj() {
        return oldObj;
    }

    public void setOldObj(String oldObj) {
        this.oldObj = oldObj;
    }

    public String getNewObj() {
        return newObj;
    }

    public void setNewObj(String newObj) {
        this.newObj = newObj;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", profileImg='" + profileImg + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", pwd='" + pwd + '\'' +
                ", address='" + address + '\'' +
                ", followed=" + followed +
                '}';
    }
}
