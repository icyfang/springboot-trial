package com.example.springjpa.repository;

import com.example.springjpa.bean.Joke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JokeRepository extends JpaRepository<Joke, String> {

}
