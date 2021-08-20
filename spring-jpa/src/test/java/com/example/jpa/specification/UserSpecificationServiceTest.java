package com.example.jpa.specification;

import com.example.jpa.querydsl.DepartmentPO;
import com.example.jpa.querydsl.UserPO;
import com.example.jpa.querydsl.UserRepository;
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
public class UserSpecificationServiceTest {

    @Autowired
    private UserSpecificationService userSpecificationService;

    @Autowired
    private UserRepository userRepository;

    private final LocalDate now = LocalDate.now();

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

        UserPO userPO1 = UserPO.builder()
                               .birthday(now)
                               .department(departmentPO)
                               .id((long) 2)
                               .firstName("first name of employee 2")
                               .lastName("last name  of employee 2")
                               .password("password of employee 2")
                               .build();

        userRepository.save(userPO);
        userRepository.save(userPO1);
    }

    @Test
    @Order(2)
    void getUser() {

        DepartmentPO departmentPO = DepartmentPO.builder()
                                                .id((long) 1)
                                                .build();
        UserPO userPO = UserPO.builder()
                              .birthday(now)
                              .firstName("first name of employee 1")
                              .department(departmentPO)
                              .build();
        List<UserPO> userlist = userSpecificationService.getUser(userPO);
        Assertions.assertEquals(1, userlist.size());
        UserPO user = userlist.get(0);
        Assertions.assertEquals(1, user.getId());
        Assertions.assertNotNull(user.getDepartment());
    }

    @Test
    @Order(2)
    void getUserBySpec() {

        DepartmentPO departmentPO = DepartmentPO.builder()
                                                .id((long) 1)
                                                .build();
        UserPO userPO = UserPO.builder()
                              .birthday(now)
                              .firstName("first name of employee 1")
                              .department(departmentPO)
                              .build();
        List<UserPO> userList = userSpecificationService.getUser(userPO);
        Assertions.assertEquals(userList, userSpecificationService.getUserBySpec(userPO));
    }

    @Test
    @Order(3)
    void getUserContactByOr() {

        DepartmentPO departmentPO = DepartmentPO.builder()
                                                .id((long) 1)
                                                .build();
        UserPO userPO = UserPO.builder()
                              .birthday(now)
                              .firstName("first name of employee 1")
                              .lastName("last name  of employee 2")
                              .department(departmentPO)
                              .build();
        List<UserPO> userList = userSpecificationService.getUserContactByOr(userPO);
        Assertions.assertEquals(2, userList.size());
        UserPO user = userList.get(1);
        Assertions.assertEquals(2, user.getId());
        Assertions.assertNotNull(user.getDepartment());
    }

    @AfterAll
    void emptyData() {
        userRepository.deleteAll();
    }
}