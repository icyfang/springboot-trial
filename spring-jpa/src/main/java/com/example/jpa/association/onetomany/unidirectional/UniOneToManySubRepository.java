package com.example.jpa.association.onetomany.unidirectional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UniOneToManySubRepository extends JpaRepository<SubPO, Long> {

}
