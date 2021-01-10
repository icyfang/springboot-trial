package com.example.jpa.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface RecordRepository extends JpaRepository<Record, Long> {

    Record findFirstByOrderByIdDesc();
}

