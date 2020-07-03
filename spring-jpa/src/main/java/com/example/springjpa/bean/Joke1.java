package com.example.springjpa.bean;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Joke1 {

    @Id
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "comment1")
    private Comment1 comment;
}
