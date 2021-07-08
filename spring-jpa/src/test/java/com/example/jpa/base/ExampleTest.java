package com.example.jpa.base;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExampleTest {

    @Autowired
    private ForumRepository forumRepository;

    @Test
    @Order(1)
    public void testAndMode() {

        Forum f = Forum.builder().userName("user").password("pwd").build();
        ExampleMatcher matcher = ExampleMatcher.matchingAll();
        forumRepository.findAll(Example.of(f, matcher));
    }

    @Test
    @Order(2)
    public void testAnyMode() {

        Forum f = Forum.builder().userName("user").password("pwd").build();
        ExampleMatcher matcher = ExampleMatcher.matching();
        forumRepository.findAll(Example.of(f, matcher));
    }

    @Test
    @Order(3)
    public void testIgnoreHandler() {

        Forum f = Forum.builder().userName("user").password("pwd").build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withNullHandler(ExampleMatcher.NullHandler.IGNORE);
        forumRepository.findAll(Example.of(f, matcher));
    }

    @Test
    @Order(4)
    public void testIncludeHandler() {

        Forum f = Forum.builder().userName("user").password("pwd").build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withNullHandler(ExampleMatcher.NullHandler.INCLUDE);
        forumRepository.findAll(Example.of(f, matcher));
    }

    @Test
    @Order(5)
    public void testStringContains() {

        Forum f = Forum.builder().userName("user").password("pwd").build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withMatcher("userName",
                                                       ExampleMatcher.GenericPropertyMatcher
                                                               .of(ExampleMatcher.StringMatcher.CONTAINING));
        forumRepository.findAll(Example.of(f, matcher));
    }

    @Test
    @Order(6)
    public void testStringContainsIgnoreCase() {

        Forum f = Forum.builder().userName("user").password("pwd").build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withMatcher("userName",
                                                       ExampleMatcher.GenericPropertyMatcher
                                                               .of(ExampleMatcher.StringMatcher.CONTAINING, true));
        forumRepository.findAll(Example.of(f, matcher));
    }

    @Test
    @Order(7)
    public void testStringContainsUsingMatchers() {

        Forum f = Forum.builder().userName("user").password("pwd").build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withMatcher("userName", ExampleMatcher.GenericPropertyMatchers
                                                       .contains());
        forumRepository.findAll(Example.of(f, matcher));
    }

    @Test
    @Order(8)
    public void testStringContainsIgnoreCaseUsingMatchers() {

        Forum f = Forum.builder().userName("user").password("pwd").build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withMatcher("userName", ExampleMatcher.GenericPropertyMatchers
                                                       .contains())
                                               .withIgnoreCase("userName");
        forumRepository.findAll(Example.of(f, matcher));
    }

    @Test
    @Order(9)
    public void testIgnorePath() {

        Forum f = Forum.builder().userName("user").password("pwd").build();
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("userName");
        forumRepository.findAll(Example.of(f, matcher));
    }

}
