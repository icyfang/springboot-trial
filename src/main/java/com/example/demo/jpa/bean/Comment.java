package com.example.demo.jpa.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Comment {
    @Id
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "joke_id")
    private Joke joke;

}
