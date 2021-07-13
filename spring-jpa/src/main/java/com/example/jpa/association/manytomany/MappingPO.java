package com.example.jpa.association.manytomany;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "manytomany_join")
@Proxy(lazy = false)
public class MappingPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "main_id")
    private Long mainId;

    @Column(name = "sub_id")
    private Long subId;
}
