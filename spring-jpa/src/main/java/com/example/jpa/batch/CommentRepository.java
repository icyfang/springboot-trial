package com.example.jpa.batch;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hodur
 * @date 2021/8/13
 */
public interface CommentRepository extends JpaRepository<CommentPO, Long>, BatchRepository<CommentPO, Long> {
}
