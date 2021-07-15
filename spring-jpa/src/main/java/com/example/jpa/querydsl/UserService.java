package com.example.jpa.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hodur
 * @date 2021/7/15
 */
@Service
public class UserService {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserRepository userRepository;

    public UserPO findByFirstNameAndLastName(String firstName, String lastName) {

        QUser user = QUser.user;
        return jpaQueryFactory
                .selectFrom(user)
                .where(
                        user.firstName.eq(firstName),
                        user.lastName.eq(lastName)
                )
                .fetchOne();

    }

    public List<UserPO> findByFirstNameOrLastName(String firstName, String lastName) {
        QUser user = QUser.user;
        return (List<UserPO>) userRepository.findAll(
                user.firstName.eq(firstName).and(user.lastName.eq(lastName)),
                user.id.asc()
        );
    }

    public long countByFirstNameLike(String firstName) {
        QUser user = QUser.user;
        return userRepository.count(
                user.firstName.like("%" + firstName + "%")
        );
    }

    public List<UserPO> findAll() {
        QUser user = QUser.user;
        return jpaQueryFactory
                .selectFrom(user)
                .orderBy(
                        user.firstName.asc()
                )
                .fetch();
    }

    public List<UserPO> findByBirthdayBetween(LocalDate start, LocalDate end) {
        QUser user = QUser.user;
        return jpaQueryFactory
                .selectFrom(user)
                .where(
                        user.birthday.between(start, end)
                )
                .fetch();
    }

    public QueryResults<UserPO> findAll(Pageable pageable) {
        QUser user = QUser.user;
        return jpaQueryFactory
                .selectFrom(user)
                .orderBy(
                        user.firstName.asc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
    }

    public List<UserDTO> findAllUserDto(Pageable pageable) {
        QUser user = QUser.user;

        return jpaQueryFactory
                .select(
                        user.id,
                        user.firstName,
                        user.lastName,
                        user.birthday,
                        user.phoneNum,
                        user.email,
                        user.address
                )
                .from(user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(tuple -> UserDTO.builder()
                                     .id(tuple.get(user.id))
                                     .firstName(tuple.get(user.firstName))
                                     .lastName(tuple.get(user.lastName))
                                     .birthday(tuple.get(user.birthday))
                                     .phoneNum(tuple.get(user.phoneNum))
                                     .email(tuple.get(user.email))
                                     .address(tuple.get(user.address))
                                     .build()
                )
                .collect(Collectors.toList());
    }

    public List<UserDTO> findAllUserDtoByProjections() {
        QUser user = QUser.user;
        return jpaQueryFactory
                .select(
                        Projections.bean(
                                UserDTO.class,
                                user.id,
                                user.firstName,
                                user.lastName,
                                user.birthday,
                                user.phoneNum,
                                user.email,
                                user.address
                        )
                )
                .from(user)
                .fetch();

    }
}
