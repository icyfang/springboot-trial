package com.example.jpa.multiDatasource.secondary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface SecondaryRepository extends JpaRepository<SecondaryPO, Integer> {

}
