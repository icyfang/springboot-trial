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

    //用户基础信息
    private Long id;
    private String firstName;    //用户名
    private String lastName;    //昵称
    private String phoneNum;
    private String email;
    private String address;
    private LocalDate birthday;    //用户生日
    //用户的部门信息
    private String deptName;    //用户所属部门
    private LocalDate deptBirth;   //部门创建的时间

}