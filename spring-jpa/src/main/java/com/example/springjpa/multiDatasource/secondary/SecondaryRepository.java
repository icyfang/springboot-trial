package com.example.springjpa.multiDatasource.secondary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface SecondaryRepository extends JpaRepository<SecondaryBean, Integer> {

}