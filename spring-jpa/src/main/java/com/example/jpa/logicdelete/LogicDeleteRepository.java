package com.example.jpa.logicdelete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Hodur
 * @date 2021/7/16
 */
public interface LogicDeleteRepository extends JpaRepository<MainPO, Long> {

    @Query("select count(*) from logic_delete_main")
    Long countAll();

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from logic_delete_sub", nativeQuery = true)
    void emptySub();

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete from logic_delete_main", nativeQuery = true)
    void emptyMain();
}
