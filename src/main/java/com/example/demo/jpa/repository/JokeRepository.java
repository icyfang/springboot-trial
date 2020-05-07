package com.example.demo.jpa.repository;

import com.example.demo.jpa.bean.Joke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JokeRepository extends JpaRepository<Joke, String> {

}
