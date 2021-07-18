package com.example.jpa.specification;

import com.example.jpa.querydsl.DepartmentPO;
import com.example.jpa.querydsl.UserPO;
import com.example.jpa.querydsl.UserRepository;
import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hodur
 * @date 2021/7/18
 */
@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")

public class UserSpecificationService {

    @Autowired
    private UserRepository userRepository;

    public List<UserPO> getUser(UserPO userPO) {
        Specification<UserPO> specification = (Specification<UserPO>) (root, query, cb) -> {

            // sort
            query.orderBy(cb.asc(root.get("id")), cb.asc(root.get("firstName")));

            List<Predicate> predicatesList = new ArrayList<>();
            if (!StringUtils.isEmpty(userPO.getFirstName())) {
                Predicate namePredicate = cb.equal(root.get("firstName"), userPO.getFirstName());
                predicatesList.add(namePredicate);
            }
            // like
            if (!StringUtils.isEmpty(userPO.getLastName())) {
                Predicate nickNamePredicate = cb.like(root.get("lastName"), "%" + userPO.getLastName() + "%");
                predicatesList.add(nickNamePredicate);
            }
            // between
            if (userPO.getBirthday() != null) {
                Predicate birthdayPredicate = cb
                        .between(root.get("birthday"), userPO.getBirthday(), LocalDate.now());
                predicatesList.add(birthdayPredicate);
            }

            //关联表查询示例
            if (userPO.getDepartment() != null) {
                Join<UserPO, DepartmentPO> joinTeacher = root.join("department", JoinType.LEFT);
                Predicate coursePredicate = cb.equal(joinTeacher.get("id"), userPO.getDepartment().getId());
                predicatesList.add(coursePredicate);
            }

            return cb.and(predicatesList.toArray(new Predicate[0]));
        };
        return userRepository.findAll(specification);
    }

    public List<UserPO> getUserContactByOr(UserPO userPO) {
        Specification<UserPO> specification = (Specification<UserPO>) (root, query, cb) -> {

            query.orderBy(cb.asc(root.get("id")), cb.asc(root.get("firstName")));

            List<Predicate> andList = new ArrayList<>();
            List<Predicate> orList = new ArrayList<>();
            if (!StringUtils.isEmpty(userPO.getFirstName())) {
                Predicate namePredicate = cb.equal(root.get("firstName"), userPO.getFirstName());
                orList.add(namePredicate);
            }
            // like
            if (!StringUtils.isEmpty(userPO.getLastName())) {
                Predicate nickNamePredicate = cb.like(root.get("lastName"), "%" + userPO.getLastName() + "%");
                orList.add(nickNamePredicate);
            }

            if (userPO.getBirthday() != null) {
                Predicate birthdayPredicate = cb
                        .between(root.get("birthday"), userPO.getBirthday(), LocalDate.now());
                andList.add(birthdayPredicate);
            }

            //关联表查询示例
            if (userPO.getDepartment() != null) {
                Join<UserPO, DepartmentPO> joinTeacher = root.join("department", JoinType.LEFT);
                Predicate coursePredicate = cb.equal(joinTeacher.get("id"), userPO.getDepartment().getId());
                andList.add(coursePredicate);
            }
            andList.add(cb.or(orList.toArray(new Predicate[0])));
            return cb.and(andList.toArray(new Predicate[0]));
        };
        return userRepository.findAll(specification);
    }

    /**
     * use jpa-spec
     * join and order not supported, use with toPredicate
     *
     * @param userPO
     * @return
     */
    public List<UserPO> getUserBySpec(UserPO userPO) {

        Specification<UserPO> specification = Specifications.<UserPO>and()
                .eq(StringUtils.isNotBlank(userPO.getFirstName()), "firstName", userPO.getFirstName())
                .like(StringUtils.isNotBlank(userPO.getLastName()), "lastName", "%" + userPO.getLastName() + "%")
                .between(userPO.getBirthday() != null, "birthday", userPO.getBirthday(), LocalDate.now())
                .build();
        Specification<UserPO> joinSpecification = (Specification<UserPO>) (root, query, cb) -> {

            // sort
            query.orderBy(cb.asc(root.get("id")), cb.asc(root.get("firstName")));

            //关联表查询示例
            if (userPO.getDepartment() != null) {
                Join<UserPO, DepartmentPO> joinTeacher = root.join("department", JoinType.LEFT);
                Predicate coursePredicate = cb.equal(joinTeacher.get("id"), userPO.getDepartment().getId());
                return cb.and(coursePredicate);
            }
            return null;
        };
        return userRepository.findAll(specification.and(joinSpecification));
    }
}
