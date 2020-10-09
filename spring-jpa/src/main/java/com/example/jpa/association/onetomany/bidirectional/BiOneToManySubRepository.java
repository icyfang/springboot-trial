package com.example.jpa.association.onetomany.bidirectional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BiOneToManySubRepository extends JpaRepository<SubPO, Long> {

}
