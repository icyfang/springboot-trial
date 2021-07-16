package com.example.jpa.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Hodur
 * @date 2020/6/20
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SecondaryTables({
        @SecondaryTable(name = "Address"),
        @SecondaryTable(name = "Comments")
})
@Table(name = "t_forum", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
@NamedQuery(name = "ForumPO.findNameNotEmpty", query = "select o from ForumPO o where o.username is not null")
@Proxy(lazy = false)
@Builder
public class ForumPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userName")
    private String username;
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
    private String comments;
    @Column(table = "comments")
    private Integer commentsLength;

}
