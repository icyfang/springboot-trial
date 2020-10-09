package com.example.jpa.association.manytomany;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity(name = "manytomany_sub")
@Proxy(lazy = false)
public class SubPO {

    @Id
    private Long id;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "manytomany_join", joinColumns = @JoinColumn(name = "sub_id"), inverseJoinColumns = @JoinColumn(name = "main_id"))
    private List<MainPO> mainPOS;

}
