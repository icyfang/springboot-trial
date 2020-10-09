package com.example.jpa.association.onetoone;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity(name = "onetoone_main")
@Proxy(lazy = false)
public class MainPO {

    @Id
    private Long id;
    private String content;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sub_id")
    private SubPO subPO;
}
