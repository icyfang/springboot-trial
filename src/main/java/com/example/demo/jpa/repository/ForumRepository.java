package com.example.demo.jpa.repository;

import com.example.demo.jpa.bean.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
}
