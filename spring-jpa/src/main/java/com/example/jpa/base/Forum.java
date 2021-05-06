package com.example.jpa.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
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
@Proxy(lazy = false)
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
    @Column(table = "address")
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
