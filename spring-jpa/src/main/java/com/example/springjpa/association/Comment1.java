package com.example.springjpa.association;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Comment1 {
    @Id
    private Long id;
    private String content;


}
