package com.example.demo.jpa.repository.primary;

import com.example.demo.jpa.bean.primary.PrimaryBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryRepository extends JpaRepository<PrimaryBean, Integer> {
}
