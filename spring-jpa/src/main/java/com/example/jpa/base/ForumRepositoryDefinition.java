package com.example.jpa.base;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Forum.class, idClass = Long.class)
public interface ForumRepositoryDefinition {

    Forum save(Forum account);

}
