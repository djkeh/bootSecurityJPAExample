package com.me.bootSecurityJPAExample.repository;

import com.me.bootSecurityJPAExample.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    User findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
}
