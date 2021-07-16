package com.example.jpa.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hodur
 * @date 2021/7/16
 */
@Service
public class ExampleService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ForumRepository forumRepository;

    /**
     * use MatchMode.ALL, the default behavior
     *
     * @param: username, password
     * @return: java.util.List<com.example.jpa.base.ForumPO>
     */
    public List<ForumPO> findByUsernameAndPassword(String username, String password) {

        ForumPO f = ForumPO.builder().username(username).password(password).build();
        ExampleMatcher matcher = ExampleMatcher.matchingAll();
        return forumRepository.findAll(Example.of(f, matcher));
    }

    /**
     * use MatchMode.ANY
     *
     * @param: username, password
     * @return: java.util.List<com.example.jpa.base.ForumPO>
     */
    public List<ForumPO> findByUsernameOrPassword(String username, String password) {

        ForumPO f = ForumPO.builder().username(username).password(password).build();
        ExampleMatcher matcher = ExampleMatcher.matchingAny();
        return forumRepository.findAll(Example.of(f, matcher));
    }

    /**
     * ignore null param, the default behavior
     *
     * @param: username, password
     * @return: java.util.List<com.example.jpa.base.ForumPO>
     */
    public List<ForumPO> findByUsernameAndPasswordIgnoreNull(String username, String password) {

        ForumPO f = ForumPO.builder().username(username).password(password).build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withNullHandler(ExampleMatcher.NullHandler.IGNORE);
        return forumRepository.findAll(Example.of(f, matcher));
    }

    /**
     * include null param
     *
     * @param: username, password
     * @return: java.util.List<com.example.jpa.base.ForumPO>
     */
    public List<ForumPO> findByUsernameAndPasswordIncludeNull(String username, String password) {

        ForumPO f = ForumPO.builder().username(username).password(password).build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withNullHandler(ExampleMatcher.NullHandler.INCLUDE);
        return forumRepository.findAll(Example.of(f, matcher));
    }

    /**
     * find by condition: username like and password equals
     *
     * @param: username, password
     * @return: java.util.List<com.example.jpa.base.ForumPO>
     */
    public List<ForumPO> findByUsernameLikeAndPassword(String username, String password) {

        ForumPO f = ForumPO.builder().username(username).password(password).build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withMatcher("username",
                                                       ExampleMatcher.GenericPropertyMatcher
                                                               .of(ExampleMatcher.StringMatcher.CONTAINING));
        return forumRepository.findAll(Example.of(f, matcher));
    }

    /**
     * same with findByUsernameLikeAndPassword, different implement
     *
     * @param: username, password
     * @return: java.util.List<com.example.jpa.base.ForumPO>
     */
    public List<ForumPO> queryByUsernameLikeAndPassword(String username, String password) {

        ForumPO f = ForumPO.builder().username(username).password(password).build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withMatcher("username", ExampleMatcher.GenericPropertyMatchers
                                                       .contains());
        return forumRepository.findAll(Example.of(f, matcher));
    }

    /**
     * find by condition: username like and password equals, ignore cae of username
     *
     * @param: username, password
     * @return: java.util.List<com.example.jpa.base.ForumPO>
     */
    public List<ForumPO> findByUsernameLikeIgnoreCaseAndPassword(String username, String password) {

        ForumPO f = ForumPO.builder().username(username).password(password).build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withMatcher("username",
                                                       ExampleMatcher.GenericPropertyMatcher
                                                               .of(ExampleMatcher.StringMatcher.CONTAINING, true));
        return forumRepository.findAll(Example.of(f, matcher));
    }

    /**
     * same with findByUsernameLikeIgnoreCaseAndPassword, different implement
     *
     * @param: username, password
     * @return: java.util.List<com.example.jpa.base.ForumPO>
     */
    public List<ForumPO> queryByUsernameLikeIgnoreCaseAndPassword(String username, String password) {

        ForumPO f = ForumPO.builder().username(username).password(password).build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withMatcher("username", ExampleMatcher.GenericPropertyMatchers
                                                       .contains())
                                               .withIgnoreCase("username");
        return forumRepository.findAll(Example.of(f, matcher));
    }

    /**
     * ignore username, just find by password
     *
     * @param: username, password
     * @return: java.util.List<com.example.jpa.base.ForumPO>
     */
    public List<ForumPO> findByPasswordIgnoreUsername(String username, String password) {

        ForumPO f = ForumPO.builder().username(username).password(password).build();
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("username");
        return forumRepository.findAll(Example.of(f, matcher));
    }

}
