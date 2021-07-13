package com.example.jpa.namegraph;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GraphMainRepository extends JpaRepository<GraphMainPO, Long> {

    @EntityGraph(value = "GraphMainPO.Graph", type = EntityGraph.EntityGraphType.FETCH)
    List<GraphMainPO> findAll();
}
