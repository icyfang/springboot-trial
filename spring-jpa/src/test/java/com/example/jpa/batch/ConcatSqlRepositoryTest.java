package com.example.jpa.batch;

import com.github.javafaker.Address;
import com.github.javafaker.Country;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.javafaker.PhoneNumber;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hodur
 * @date 2021/8/28
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConcatSqlRepositoryTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private BatchUserRepository batchUserRepository;

    @Test
    void batchInsert() throws IllegalAccessException {
        Faker faker = new Faker();
        Name name = faker.name();
        PhoneNumber phoneNumber = faker.phoneNumber();
        Country country = faker.country();
        Address address = faker.address();
        List<BatchUserPO> l = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            BatchUserPO user = new BatchUserPO();
            user.setId((long) i);
            user.setFirstname(name.firstName());
            user.setLastname(name.lastName());
            user.setFullName(name.fullName());
            user.setNickname(name.name());
            user.setDescription("ald;sfasjdlkflkjasdljfkl;adlsjfljlasdfsd");
            user.setPhoneNum(phoneNumber.phoneNumber());
            user.setEmail("adfa;sjklajsdklflajsd;lfalsdklfa");
            user.setCountry(country.name());
            user.setCity(address.city());
            user.setAddress(address.fullAddress());
            user.setBirthday(LocalDate.now());
            user.setRegisterTime(LocalDateTime.now());
            user.setProvisionDate(new Date());
            l.add(user);
        }
        batchUserRepository.batchInsert(l);

    }

}