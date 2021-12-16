package com.example.jpa.association.onetomany.unidirectional;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.List;

/**
 * @author Hodur
 * @date 2020/10/10
 */
@Data
@Entity(name = "onetomany_uni_main")
@Proxy(lazy = false)
public class MainPO {

    @Id
    private Long id;
    private String content;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "main_id")
    @OrderBy("name")
    private List<SubPO> subPOS;
}
