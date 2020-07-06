package com.example.springjpa.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SecondaryTables({
        @SecondaryTable(name = "Address"),
        @SecondaryTable(name = "Comments")
})
@Table(name = "t_forum")
@NamedQuery(name = "Forum.findNameNotEmpty", query = "select o from Forum o where o.userName is not null")
public class Forum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    @Column(columnDefinition = "DECIMAL(19, 2)")
    private BigDecimal account;

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

}