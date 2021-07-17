package com.example.jpa.querydsl.multitable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private EmployeeRepository employeeRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private EmployeeWithoutFKRepository employeeWithoutFKRepository;

    private LocalDate now = LocalDate.now();

    @Test
    public void initData() {

        DepartmentPO departmentPO = DepartmentPO.builder().createDate(now)
                                                .deptName("department 1")
                                                .id((long) 1)
                                                .build();
        EmployeePO employeePO = EmployeePO.builder()
                                          .birthday(now)
                                          .department(departmentPO)
                                          .id((long) 1)
                                          .nickName("nick name of employee 1")
                                          .password("password of employee 1")
                                          .username("username  of employee 1")
                                          .build();

        EmployeeWithoutFKPO employeeWithoutFK = EmployeeWithoutFKPO.builder()
                                                                   .birthday(now)
                                                                   .departmentId((long) 1)
                                                                   .id((long) 1)
                                                                   .nickName("nick name of employee 1")
                                                                   .password("password of employee 1")
                                                                   .username("username  of employee 1")
                                                                   .build();
        employeeRepository.save(employeePO);
        employeeWithoutFKRepository.save(employeeWithoutFK);
    }

    @Test
    void findEmployeeDTOByDepartmentId() {

        List<EmployeeDTO> employeeDTOList = employeeService.findEmployeeDTOByDepartmentId((long) 1);
        Assertions.assertEquals(1, employeeDTOList.size());
        EmployeeDTO employeeDTO = employeeDTOList.get(0);
        Assertions.assertEquals(now, employeeDTO.getBirthday());
        Assertions.assertEquals(now, employeeDTO.getDeptBirth());
        Assertions.assertEquals("department 1", employeeDTO.getDeptName());
        Assertions.assertEquals("nick name of employee 1", employeeDTO.getNickname());
        Assertions.assertEquals("username  of employee 1", employeeDTO.getUsername());
    }

    @Test
    void queryEmployeeDTOByDepartmentId() {

        List<EmployeeDTO> employeeDTOList = employeeService.queryEmployeeDTOByDepartmentId((long) 1);
        Assertions.assertEquals(1, employeeDTOList.size());
        EmployeeDTO employeeDTO = employeeDTOList.get(0);
        Assertions.assertEquals(now, employeeDTO.getBirthday());
        Assertions.assertEquals(now, employeeDTO.getDeptBirth());
        Assertions.assertEquals("department 1", employeeDTO.getDeptName());
        Assertions.assertEquals("nick name of employee 1", employeeDTO.getNickname());
        Assertions.assertEquals("username  of employee 1", employeeDTO.getUsername());
    }

    @Test
    void emptyData() {
        employeeRepository.deleteAll();
        employeeWithoutFKRepository.deleteAll();
    }
}