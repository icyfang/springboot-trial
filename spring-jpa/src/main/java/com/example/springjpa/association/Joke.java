package com.example.springjpa.association;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Joke {

    @Id
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "joke_id")
    private List<Comment> comments;

}
