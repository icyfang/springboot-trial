package com.example.jpa.batch;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Hodur
 * @date 2021/8/13
 */
@Entity
@Table(name = "t_comment")
@Getter
@Setter
public class CommentPO {

    @Id
    private Long id;
    private String name;
    private String content;
}
