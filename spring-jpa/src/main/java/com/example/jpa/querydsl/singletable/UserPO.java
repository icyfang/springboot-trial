package com.example.jpa.querydsl.singletable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
//@EntityListeners(value = AuditingEntityListener.class)
public class UserPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String phoneNum;
    private String email;
    private String address;

    @LastModifiedDate
    private LocalDateTime updateTime;
    @CreatedDate
    private LocalDateTime createTime;
}
