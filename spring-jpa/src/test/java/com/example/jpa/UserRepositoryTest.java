package com.example.jpa;

import com.example.jpa.base.User;
import com.example.jpa.base.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Hodur
 * @date 2020/12/3
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void initData() {
        Faker faker = new Faker(Locale.SIMPLIFIED_CHINESE);
        List<User> collect = IntStream.range(1, 80000)
                                      .mapToObj(i -> new User(null,
                                              faker.name().name(),
                                              faker.address().fullAddress(),
                                              faker.company().industry(),
                                              LocalDateTime.now()))
                                      .collect(Collectors.toList());

        userRepository.saveAll(collect);
    }
}
