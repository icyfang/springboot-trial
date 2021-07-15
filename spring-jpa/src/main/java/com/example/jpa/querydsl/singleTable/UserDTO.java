package com.example.jpa.querydsl.singleTable;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Hodur
 * @date 2021/7/15
 */
@Data
@Builder
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String phoneNum;
    private String email;
    private String address;
}
