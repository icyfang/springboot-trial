package com.example.jpa.querydsl.multitable;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @date 2021/7/17
 */
@Service
public class EmployeeService {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public List<EmployeeDTO> findEmployeeDTOByDepartmentId(Long departmentId) {
        QEmployeePO employee = QEmployeePO.employeePO;
        QDepartmentPO department = QDepartmentPO.departmentPO;
        //直接返回
        return jpaQueryFactory
                //投影只去部分字段
                .select(
                        employee.username,
                        employee.nickName,
                        employee.birthday,
                        department.deptName,
                        department.createDate

                )
                .from(employee)
                //联合查询
                .join(employee.department, department)
                .where(department.id.eq(departmentId))
                .fetch()
                //lambda开始
                .stream()
                .map(tuple ->
                        //需要做类型转换，所以使用map函数非常适合
                        EmployeeDTO.builder()
                                   .username(tuple.get(employee.username))
                                   .nickname(tuple.get(employee.nickName))
                                   .birthday(tuple.get(employee.birthday))
                                   .deptName(tuple.get(department.deptName))
                                   .deptBirth(tuple.get(department.createDate))
                                   .build()
                )
                .collect(Collectors.toList());
    }

    /**
     * @param departmentId
     * @return
     */
    public List<EmployeeDTO> queryEmployeeDTOByDepartmentId(Long departmentId) {

        QEmployeeWithoutFKPO employee = QEmployeeWithoutFKPO.employeeWithoutFKPO;
        QDepartmentPO department = QDepartmentPO.departmentPO;
        //直接返回
        return jpaQueryFactory
                //投影只去部分字段
                .select(
                        employee.username,
                        employee.nickName,
                        employee.birthday,
                        department.deptName,
                        department.createDate

                )
                .from(employee, department)
                //联合查询
                .where(
                        employee.departmentId.eq(department.id).and(department.id.eq(departmentId))
                )
                .fetch()
                //lambda开始
                .stream()
                .map(tuple ->
                        //需要做类型转换，所以使用map函数非常适合
                        EmployeeDTO.builder()
                                   .username(tuple.get(employee.username))
                                   .nickname(tuple.get(employee.nickName))
                                   .birthday(tuple.get(employee.birthday))
                                   .deptName(tuple.get(department.deptName))
                                   .deptBirth(tuple.get(department.createDate))
                                   .build()
                )
                .collect(Collectors.toList());
    }
}
