package com.example.demo.bean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@SecondaryTables({
        @SecondaryTable(name = "Address"),
        @SecondaryTable(name = "Comments")
})
public class Forum implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Column(table = "address", length = 100)
    private String street;
    @Column(table = "address", nullable = false)
    private String city;
    @Column(table = "address")
    private String country;
    @Column(table = "comments")
    private String title;
    @Column(table = "comments")
    private String Comments;
    @Column(table = "comments")
    private Integer comments_length;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public Integer getComments_length() {
        return comments_length;
    }

    public void setComments_length(Integer comments_length) {
        this.comments_length = comments_length;
    }
}