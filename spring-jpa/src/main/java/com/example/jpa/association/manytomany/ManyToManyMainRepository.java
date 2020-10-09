package com.example.jpa.association.manytomany;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManyToManyMainRepository extends JpaRepository<MainPO, Long> {
}
