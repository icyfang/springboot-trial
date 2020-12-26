package com.example.jpa;

import com.example.jpa.base.User;
import com.example.jpa.base.UserRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
        Faker englishFaker = new Faker(Locale.ENGLISH);
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        for (int j = 1; j < 10; j++) {

            List<User> collect = IntStream.range(1, 100000)
                                          .mapToObj(i -> new User(null,
                                                  faker.name().name(),
                                                  englishFaker.name().lastName(),
                                                  englishFaker.name().firstName(),
                                                  englishFaker.demographic().sex(),
                                                  faker.name().bloodGroup(),
                                                  LocalParser.toLocalDate(faker.date().birthday()),
                                                  faker.phoneNumber().phoneNumber(),
                                                  fakeValuesService.bothify("????##@gmail.com"),
                                                  faker.address().fullAddress(),
                                                  faker.job().title(),
                                                  faker.company().industry(),
                                                  LocalDateTime.now(),
                                                  LocalDateTime.now()))
                                          .collect(Collectors.toList());

            userRepository.saveAll(collect);
        }
    }
}

class LocalParser {

    public static LocalDate toLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date toDate(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
}
