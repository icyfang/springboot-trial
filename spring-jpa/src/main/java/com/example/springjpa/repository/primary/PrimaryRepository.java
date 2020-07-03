package com.example.springjpa.repository.primary;

import com.example.springjpa.bean.primary.PrimaryBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryRepository extends JpaRepository<PrimaryBean, Integer> {
}
