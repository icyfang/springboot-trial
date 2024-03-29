package com.example.jpa.association.manytomany;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * @author Hodur
 * @date 2020/10/10
 */
@Getter
@Setter
@Entity(name = "manytomany_main")
@Proxy(lazy = false)
public class MainPO {

    @Id
    private Long id;

    private String content;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "manytomany_join", joinColumns = @JoinColumn(name = "main_id"), inverseJoinColumns = @JoinColumn(name = "sub_id"))
    private List<SubPO> subPO;

}
