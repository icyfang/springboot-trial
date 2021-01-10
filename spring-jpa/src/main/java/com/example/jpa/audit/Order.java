package com.example.jpa.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Shanghong Cai
 * @since 2021-01-06
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_order")
@EntityListeners(value = {AuditingEntityListener.class, RecordEntityListener.class})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int status;

    @LastModifiedDate
    private LocalDateTime updateTime;
    @CreatedDate
    private LocalDateTime createTime;

    @CreatedBy
    @JoinColumn(name = "create_userId")
    @OneToOne
    private User createUser;

    @LastModifiedBy
    @JoinColumn(name = "update_userId")
    @OneToOne
    private User updateUser;
}
