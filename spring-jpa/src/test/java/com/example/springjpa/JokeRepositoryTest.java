package com.example.springjpa;

import com.example.springjpa.bean.Comment;
import com.example.springjpa.bean.Comment1;
import com.example.springjpa.bean.Joke;
import com.example.springjpa.bean.Joke1;
import com.example.springjpa.repository.Joke1Repository;
import com.example.springjpa.repository.JokeRepository;
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

    @Autowired
    private Joke1Repository joke1Repository;

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

    @Test
    public void test1() {
        Joke1 joke = new Joke1();
        joke.setId((long) 1);
        joke.setName("joke1");
        Comment1 comment = new Comment1();
        comment.setId((long) 3);
        comment.setContent("content");
        joke.setComment(comment);
        joke1Repository.save(joke);
    }
}