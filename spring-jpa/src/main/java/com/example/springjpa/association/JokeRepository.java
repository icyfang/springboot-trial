package com.example.springjpa.association;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JokeRepository extends JpaRepository<Joke, String> {

}
