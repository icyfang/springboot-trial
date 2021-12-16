package com.example.jpa.association.manytomany;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hodur
 * @date 2020/10/10
 */
public interface ManyToManyMainRepository extends JpaRepository<MainPO, Long> {
}
