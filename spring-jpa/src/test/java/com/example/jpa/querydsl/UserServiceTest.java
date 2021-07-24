package com.example.jpa.querydsl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Autowired
    private UserService employeeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserWithoutFKRepository userWithoutFKRepository;

    private LocalDate now = LocalDate.now();

    @BeforeAll
    public void initData() {

        DepartmentPO departmentPO = DepartmentPO.builder().createDate(now)
                                                .deptName("department 1")
                                                .id((long) 1)
                                                .build();
        UserPO userPO = UserPO.builder()
                              .birthday(now)
                              .department(departmentPO)
                              .id((long) 1)
                              .firstName("first name of employee 1")
                              .lastName("last name  of employee 1")
                              .password("password of employee 1")
                              .build();

        UserWithoutFKPO employeeWithoutFK = UserWithoutFKPO.builder()
                                                           .birthday(now)
                                                           .departmentId((long) 1)
                                                           .id((long) 1)
                                                           .firstName("first name of employee 1")
                                                           .lastName("last name  of employee 1")
                                                           .password("password of employee 1")
                                                           .build();
        userRepository.save(userPO);
        userWithoutFKRepository.save(employeeWithoutFK);
    }

    @Test
    @Order(2)
    void findEmployeeDTOByDepartmentId() {

        List<UserDTO> userDTOList = employeeService.findUserDTOByDepartmentId((long) 1);
        Assertions.assertEquals(1, userDTOList.size());
        UserDTO userDTO = userDTOList.get(0);
        Assertions.assertEquals(now, userDTO.getBirthday());
        Assertions.assertEquals(now, userDTO.getDeptBirth());
        Assertions.assertEquals("department 1", userDTO.getDeptName());
        Assertions.assertEquals("first name of employee 1", userDTO.getLastName());
        Assertions.assertEquals("last name  of employee 1", userDTO.getFirstName());
    }

    @Test
    @Order(3)
    void queryEmployeeDTOByDepartmentId() {

        List<UserDTO> userDTOList = employeeService.queryUserDTOByDepartmentId((long) 1);
        Assertions.assertEquals(1, userDTOList.size());
        UserDTO userDTO = userDTOList.get(0);
        Assertions.assertEquals(now, userDTO.getBirthday());
        Assertions.assertEquals(now, userDTO.getDeptBirth());
        Assertions.assertEquals("department 1", userDTO.getDeptName());
        Assertions.assertEquals("first name of employee 1", userDTO.getLastName());
        Assertions.assertEquals("last name  of employee 1", userDTO.getFirstName());
    }

    @AfterAll
    void emptyData() {
        userRepository.deleteAll();
        userWithoutFKRepository.deleteAll();
    }
}