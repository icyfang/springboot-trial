package com.example.jpa.querydsl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * @author Hodur
 * @date 2021/7/17
 */

@Data
@Entity
@Table(name = "t_user_without_fk")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithoutFKPO {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNum;
    private String email;
    private String address;
    private LocalDate birthday;

    @Column(name = "department_id")
    private Long departmentId;
}