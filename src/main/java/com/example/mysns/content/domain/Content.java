package com.example.mysns.content.domain;

public class Content {

    private int idx;
    private String img;
    private String title;
    private String content;
    private String nick;
    private int m_id;

    private boolean mine;
    private int page;
    private String searchWord;


    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    @Override
    public String toString() {
        return "Content{" +
                "idx='" + idx + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", nick='" + nick + '\'' +
                ", m_idx='" + m_id + '\'' +
                ", mine=" + mine +
                ", page=" + page +
                '}';
    }
}
