package com.example.jpa.multidatasource.primary;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryRepository extends JpaRepository<PrimaryPO, Integer> {
}
