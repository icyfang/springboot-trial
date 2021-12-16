package com.example.jpa.base;

import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author Hodur
 * @date 2020/07/06
 */
@RepositoryDefinition(domainClass = ForumPO.class, idClass = Long.class)
public interface ForumRepository2 {

    ForumPO save(ForumPO account);

}
