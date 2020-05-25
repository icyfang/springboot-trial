package com.example.demo.jpa.repository.secondary;

import com.example.demo.jpa.bean.secondary.SecondaryBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryRepository extends JpaRepository<SecondaryBean, Integer> {

}
