package com.example.springjpa.repository;

import com.example.springjpa.bean.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
}
