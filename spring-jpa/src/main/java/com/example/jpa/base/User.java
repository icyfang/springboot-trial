package com.example.jpa.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Hodur
 * @date 2020/12/3
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String firstName;
    private String gender;
    private String bloodGroup;
    private LocalDate birthday;
    private String phoneNum;
    private String email;
    private String address;
    private String occupation;
    private String description;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
