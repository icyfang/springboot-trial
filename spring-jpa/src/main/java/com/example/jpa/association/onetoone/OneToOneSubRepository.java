package com.example.jpa.association.onetoone;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hodur
 * @date 2020/10/10
 */
public interface OneToOneSubRepository extends JpaRepository<SubPO, Long> {

}
