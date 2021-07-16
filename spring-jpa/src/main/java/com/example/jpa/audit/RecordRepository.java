package com.example.jpa.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<RecordPO, Long> {

    RecordPO findFirstByOrderByIdDesc();
}

