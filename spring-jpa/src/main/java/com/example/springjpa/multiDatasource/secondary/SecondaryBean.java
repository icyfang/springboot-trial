package com.example.springjpa.multiDatasource.secondary;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_secondary")
public class SecondaryBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "col_name")
    private String name;

}
