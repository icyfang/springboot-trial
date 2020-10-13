package com.example.jpa.association.onetomany.unidirectional;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity(name = "onetomany_uni_main")
@Proxy(lazy = false)
public class MainPO {

    @Id
    private Long id;
    private String content;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "main_id")
    private List<SubPO> subPOS;
}