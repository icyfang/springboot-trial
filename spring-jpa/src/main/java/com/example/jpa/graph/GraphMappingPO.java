package com.example.jpa.graph;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Hodur
 * @date 2021/07/13
 */
@Getter
@Setter
@Entity(name = "t_graph_join")
@Proxy(lazy = false)
public class GraphMappingPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "main_id")
    private Long mainId;

    @Column(name = "sub_id")
    private Long subId;
}
