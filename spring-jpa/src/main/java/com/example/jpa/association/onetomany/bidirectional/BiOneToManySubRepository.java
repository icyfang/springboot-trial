package com.example.jpa.association.onetomany.bidirectional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hodur
 * @date 2020/10/10
 */
public interface BiOneToManySubRepository extends JpaRepository<SubPO, Long> {

}
