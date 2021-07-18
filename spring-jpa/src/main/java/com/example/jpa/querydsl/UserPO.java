package com.example.jpa.querydsl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * @author Hodur
 * @date 2021/7/17
 */

@Data
@Entity
@Table(name = "t_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPO {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNum;
    private String email;
    private String address;
    private LocalDate birthday;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private DepartmentPO department; //部门实体

//    @Column(name = "department_id")
//    private Long departmentId;
}