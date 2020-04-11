package com.example.demo.repository;

import com.example.demo.bean.User;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository2 {
    User save(User entity);

    Iterable<User> findAll();
}
