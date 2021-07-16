package com.example.jpa.graph;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GraphMainRepository extends JpaRepository<GraphMainPO, Long> {

    @Override
    @EntityGraph(value = "GraphMainPO.Graph", type = EntityGraph.EntityGraphType.FETCH)
    List<GraphMainPO> findAll();

    @Override
    @EntityGraph(value = "GraphMainPO.Graph", type = EntityGraph.EntityGraphType.FETCH)
    Page<GraphMainPO> findAll(Pageable pageable);

}
