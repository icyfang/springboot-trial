package com.example.springjpa.base;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Forum.class, idClass = Long.class)
public interface ForumRepository2 {

    Forum save(Forum account);

}
