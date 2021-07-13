package com.example.jpa.association.onetoone;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OneToOneMainRepository extends JpaRepository<MainPO, Long> {

}
