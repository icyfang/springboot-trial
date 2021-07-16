package com.example.jpa.base;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = ForumPO.class, idClass = Long.class)
public interface ForumRepository2 {

    ForumPO save(ForumPO account);

}
