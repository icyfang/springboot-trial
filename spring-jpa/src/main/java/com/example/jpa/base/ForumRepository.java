package com.example.jpa.base;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {

    @Query(value = "select u from Forum u where u.userName=:userName")
    Forum findForum(@Param("userName") String name);

    List<Forum> findNameNotEmpty();

    Forum findByUserNameAndAccount(String name, BigDecimal account);

    List<Forum> findByUserName(String name, Sort sort);

    List<Forum> findByAccountBetween(BigDecimal off, BigDecimal end);

    List<Forum> findTop3ByUserName(String name);

    List<Forum> findLast3ByUserName(String name);

}

