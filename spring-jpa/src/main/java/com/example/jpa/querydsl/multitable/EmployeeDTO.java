package com.example.jpa.querydsl.multitable;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Hodur
 * @date 2021/7/17
 */
@Data
@Builder
public class EmployeeDTO {

    //用户基础信息
    private String username;    //用户名
    private String nickname;    //昵称
    private LocalDate birthday;    //用户生日
    //用户的部门信息
    private String deptName;    //用户所属部门
    private LocalDate deptBirth;   //部门创建的时间

}