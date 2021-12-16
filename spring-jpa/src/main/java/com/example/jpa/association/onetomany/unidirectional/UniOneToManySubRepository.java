package com.example.jpa.association.onetomany.unidirectional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hodur
 * @date 2020/10/10
 */
public interface UniOneToManySubRepository extends JpaRepository<SubPO, Long> {

}
