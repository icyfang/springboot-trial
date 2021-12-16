package com.example.jpa.association.onetomany.unidirectional;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Hodur
 * @date 2020/10/10
 */
@Data
@Entity(name = "onetomany_uni_sub")
public class SubPO {
    @Id
    private Long id;
    private String name;
}
