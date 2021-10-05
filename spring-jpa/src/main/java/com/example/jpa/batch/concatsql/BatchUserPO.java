package com.example.jpa.batch.concatsql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Hodur
 * @date 2021/8/28
 */
@Data
@Entity
@Table(name = "t_user_batch")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchUserPO {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String fullName;
    private String nickname;
    private String description;
    private String phoneNum;
    private String email;
    private String country;
    private String city;
    private String address;
    private LocalDate birthday;
    private LocalDateTime registerTime;
    private Date provisionDate;
}
