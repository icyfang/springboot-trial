package com.example.jpa.multiDatasource.primary;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryRepository extends JpaRepository<PrimaryPO, Integer> {
}
