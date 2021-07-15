package com.example.jpa.audit;

import com.example.jpa.querydsl.singleTable.UserPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Hodur
 * @date 2021-01-06
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_record")
@EntityListeners(value = {AuditingEntityListener.class})
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    @Column(columnDefinition = "varchar(2048)")
    private String content;

    @CreatedDate
    private LocalDateTime createTime;

    @CreatedBy
    @OneToOne
    @JoinColumn(name = "create_userId", foreignKey = @ForeignKey(name = "none"))
    private UserPO createUser;
}
