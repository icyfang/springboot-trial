package com.example.jpa.association.onetoone;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Hodur
 * @date 2020/10/10
 */
@Data
@Entity
@Table(name = "onetoone_sub")
public class SubPO {
    @Id
    private Long id;
    private String name;
}
