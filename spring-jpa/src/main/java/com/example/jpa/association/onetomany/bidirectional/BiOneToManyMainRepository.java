package com.example.jpa.association.onetomany.bidirectional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Houdr
 * @date 2020/10/10
 */
public interface BiOneToManyMainRepository extends JpaRepository<MainPO, Long> {

}
