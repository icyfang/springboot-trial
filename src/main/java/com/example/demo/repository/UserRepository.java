package com.example.demo.repository;

import com.example.demo.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    User findByNameAndAge(String name, Integer age);

    @Query(value = "select * from user u where u.name=:name", nativeQuery = true)
    User findUser(@Param("name") String name);

    List<User> find1(int age);

    @Transactional
    void deleteByName(String name);

    User findFirstByName(String name);

    User findTopByName(String name);

    List<User> findFirst3ByName(String name);

    List<User> findTop3ByName(String name);
}