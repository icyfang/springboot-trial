package com.example.springjpa.multiDatasource.primary;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_primary")
public class PrimaryBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "col_name")
    private String name;

}
