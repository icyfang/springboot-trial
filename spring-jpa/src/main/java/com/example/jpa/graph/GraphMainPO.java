package com.example.jpa.graph;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Hodur
 * @date 2021/07/13
 */
@Getter
@Setter
@Entity
@Table(name = "t_graph_main")
@NamedEntityGraph(name = "GraphMainPO.Graph", attributeNodes = {@NamedAttributeNode("subPOList")})
public class GraphMainPO {

    @Id
    private Long id;

    private String content;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t_graph_join", joinColumns = @JoinColumn(name = "main_id")
            , inverseJoinColumns = @JoinColumn(name = "sub_id"))
    private List<GraphSubPO> subPOList;

}
