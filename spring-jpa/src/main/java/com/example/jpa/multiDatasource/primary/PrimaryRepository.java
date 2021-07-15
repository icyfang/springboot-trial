package com.example.jpa.multiDatasource.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PrimaryRepository extends JpaRepository<PrimaryPO, Integer> {
}
