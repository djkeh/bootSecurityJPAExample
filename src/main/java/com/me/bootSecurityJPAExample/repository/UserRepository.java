package com.me.bootSecurityJPAExample.repository;

import com.me.bootSecurityJPAExample.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    User findByLoginId(@Param("loginId") String loginId);
    boolean existsByLoginId(@Param("loginId") String loginId);
}
