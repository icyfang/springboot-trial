package com.example.jpa.querydsl.singleTable;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public UserPO queryByFirstNameAndLastName(String firstName, String lastName) {

        QUserPO user = QUserPO.user;
        return jpaQueryFactory
                .selectFrom(user)
                .where(
                        user.firstName.eq(firstName),
                        user.lastName.eq(lastName)
                )
                .fetchOne();

    }

    public List<UserPO> queryByBirthdayBetween(LocalDate start, LocalDate end) {
        QUserPO user = QUserPO.user;
        return jpaQueryFactory
                .selectFrom(user)
                .where(
                        user.birthday.between(start, end)
                )
                .fetch();
    }

    public List<UserPO> queryAll() {
        QUserPO user = QUserPO.user;
        return jpaQueryFactory
                .selectFrom(user)
                .orderBy(
                        user.firstName.asc()
                )
                .fetch();
    }

    public QueryResults<UserPO> queryAll(Pageable pageable) {
        QUserPO user = QUserPO.user;
        return jpaQueryFactory
                .selectFrom(user)
                .orderBy(
                        user.firstName.asc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
    }

    public List<UserDTO> queryAllUserDto(Pageable pageable) {
        QUserPO user = QUserPO.user;

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

    public List<UserDTO> queryAllUserDtoByProjections() {
        QUserPO user = QUserPO.user;
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

    public List<UserPO> queryByUserPropertiesGroupByAddress(String firstName, String lastName, String phoneNum, LocalDate birthday, String address) {

        QUserPO user = QUserPO.user;
        Predicate predicate = user.isNotNull().or(user.isNull());

        //执行动态条件拼装
        predicate = firstName == null ? predicate : ExpressionUtils.and(predicate, user.firstName.eq(firstName));
        predicate = lastName == null ? predicate : ExpressionUtils.and(predicate, user.lastName.eq(lastName));
        predicate = phoneNum == null ? predicate : ExpressionUtils.and(predicate, user.phoneNum.eq(phoneNum));
        predicate = birthday == null ? predicate : ExpressionUtils.and(predicate, user.birthday.eq(birthday));
        predicate = address == null ? predicate : ExpressionUtils.and(predicate, user.address.eq(address));

        return jpaQueryFactory
                .selectFrom(user)
                .where(predicate)               //执行条件
                .orderBy(user.id.asc())     //执行排序
                .groupBy(user.address)           //执行分组
                .fetch();
    }

    public List<UserPO> findByFirstNameOrLastName(String firstName, String lastName) {
        QUserPO user = QUserPO.user;
        return (List<UserPO>) userRepository.findAll(
                user.firstName.eq(firstName).and(user.lastName.eq(lastName)),
                user.id.asc()
        );
    }

    public long countByFirstNameLike(String firstName) {
        QUserPO user = QUserPO.user;
        return userRepository.count(
                user.firstName.like("%" + firstName + "%")
        );
    }

    public Page<UserPO> findByUserProperties(Pageable pageable, String firstName, String lastName, String phoneNum, LocalDate birthday) {

        QUserPO user = QUserPO.user;
        Predicate predicate = user.isNotNull().or(user.isNull());

        //执行动态条件拼装
        predicate = firstName == null ? predicate : ExpressionUtils.and(predicate, user.firstName.eq(firstName));
        predicate = lastName == null ? predicate : ExpressionUtils.and(predicate, user.lastName.eq(lastName));
        predicate = phoneNum == null ? predicate : ExpressionUtils.and(predicate, user.phoneNum.eq(phoneNum));
        predicate = birthday == null ? predicate : ExpressionUtils.and(predicate, user.birthday.eq(birthday));

        return userRepository.findAll(predicate, pageable);
    }

}
