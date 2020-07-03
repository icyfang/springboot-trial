package com.example.springjpa.repository.secondary;

import com.example.springjpa.bean.secondary.SecondaryBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryRepository extends JpaRepository<SecondaryBean, Integer> {

}
