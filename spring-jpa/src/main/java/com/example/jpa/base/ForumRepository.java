package com.example.jpa.base;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Hodur
 * @date 2020/07/06
 */
public interface ForumRepository extends JpaRepository<ForumPO, Long> {

    @Query(value = "select u from ForumPO u where u.username=:username")
    ForumPO findForum(@Param("username") String name);

    List<ForumPO> findNameNotEmpty();

    ForumPO findByUsernameAndAccount(String name, BigDecimal account);

    List<ForumPO> findByUsername(String name, Sort sort);

    List<ForumPO> findByAccountBetween(BigDecimal off, BigDecimal end);

    List<ForumPO> findTop3ByUsername(String name);

    List<ForumPO> findLast3ByUsername(String name);

}

