package com.example.demo.jpa;

import com.example.demo.jpa.bean.Comment;
import com.example.demo.jpa.bean.Joke;
import com.example.demo.jpa.repository.JokeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JokeRepositoryTest {

    @Autowired
    private JokeRepository jokeRepository;

    @Test
    public void test() {
        Joke joke = new Joke();
        joke.setId((long) 1);
        joke.setName("joke1");
        Comment comment = new Comment();
        comment.setId((long) 3);
        comment.setContent("content");
        joke.setComments(Collections.singletonList(comment));
        jokeRepository.save(joke);
    }
}
