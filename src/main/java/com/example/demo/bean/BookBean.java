package com.example.demo.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

//@Component
@ConfigurationProperties("book")
public class BookBean {
    private String name;
    private String author;
    private List<String> chapters;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getChapters() {
        return chapters;
    }

    public void setChapters(List<String> chapters) {
        this.chapters = chapters;
    }
}
