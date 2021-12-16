package com.example.jpa.multidatasource.secondary;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hodur
 * @date 2020/05/25
 */
public interface SecondaryRepository extends JpaRepository<SecondaryPO, Integer> {

}
