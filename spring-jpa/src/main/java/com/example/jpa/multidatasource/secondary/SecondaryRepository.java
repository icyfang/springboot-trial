package com.example.jpa.multidatasource.secondary;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryRepository extends JpaRepository<SecondaryPO, Integer> {

}
