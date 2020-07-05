package com.example.springjpa.association;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class Joke1 {

    @Id
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Comment1 comment;
}
