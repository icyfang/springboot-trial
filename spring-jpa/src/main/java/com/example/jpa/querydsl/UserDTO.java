package com.example.jpa.querydsl;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Hodur
 * @date 2021/7/17
 */
@Data
@Builder
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String email;
    private String address;
    private LocalDate birthday;

    private String deptName;
    private LocalDate deptBirth;

}