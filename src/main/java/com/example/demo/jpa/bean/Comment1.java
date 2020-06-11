package com.example.demo.jpa.bean;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment1 {
    @Id
    private Long id;
    private String content;


}
