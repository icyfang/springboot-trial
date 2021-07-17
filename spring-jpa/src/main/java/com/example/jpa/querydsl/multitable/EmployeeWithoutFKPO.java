package com.example.jpa.querydsl.multitable;

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
@Table(name = "t_employee_without_fk")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithoutFKPO {

    @Id
    private Long id;
    private String username;
    private String nickName;
    private String password;
    private LocalDate birthday;

    @Column(name = "department_id")
    private Long departmentId;
}