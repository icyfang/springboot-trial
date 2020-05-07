package com.example.demo.jpa.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Joke {

    @Id
    private Long id;
    private String name;

    @OneToMany
    @JoinColumn(name = "joke_id")
    private List<Comment> comments;

}
