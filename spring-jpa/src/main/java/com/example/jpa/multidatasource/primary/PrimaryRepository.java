package com.example.jpa.multidatasource.primary;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hodur
 * @date 2020/05/25
 */
public interface PrimaryRepository extends JpaRepository<PrimaryPO, Integer> {
}
