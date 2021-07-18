package com.example.jpa.querydsl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "t_department")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentPO {
    @Id
    private Long id;    //部门id
    private String deptName;   //部门名称
    private LocalDate createDate;   //创建时间
}