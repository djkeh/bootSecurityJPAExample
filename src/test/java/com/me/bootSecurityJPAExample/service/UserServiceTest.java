package com.me.bootSecurityJPAExample.service;

import com.me.bootSecurityJPAExample.domain.User;
import com.me.bootSecurityJPAExample.repository.UserRepository;
import org.h2.jdbc.JdbcSQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("User service logic tests")
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @BeforeEach
    void before() {
        userRepository.deleteAll(); // To remove sample data.
        userRepository.save(User.builder()
                .loginId("test")
                .password("password")
                .createdBy("creator")
                .updatedBy("creator")
                .build()
        );
    }


    @Test
    @DisplayName("Get User list containing single User")
    void getList() {
        // Given

        // When
        List<User> list = userService.getList();

        // Then
        assertThat(list).hasSize(1);
        assertThat(list.get(0)).hasFieldOrPropertyWithValue("loginId", "test");
    }

    @Test
    @DisplayName("Get a User")
    void getUser() {
        // Given

        // When
        User user = userService.getUser("test");

        // Then
        assertThat(user)
                .hasFieldOrPropertyWithValue("loginId", "test")
                .hasFieldOrPropertyWithValue("password", "password")
                .hasFieldOrPropertyWithValue("createdBy", "creator")
                .hasFieldOrPropertyWithValue("updatedBy", "creator");
    }

    @Test
    @DisplayName("Add a normal User")
    void addUser() {
        // Given
        User user = User.builder()
                .loginId("test2")
                .password("password2")
                .createdBy("creator2")
                .updatedBy("creator2")
                .build();

        // When
        userService.addUser(user);
        List<User> result = userRepository.findAll();

        // Then
        assertThat(result)
                .hasSize(2)
                .contains(user);
    }

    @Test
    @DisplayName("Add a User with a missing field")
    void addIncompleteUser() {
        // Given
        User user = User.builder()
                .loginId("test2")
                .password("password2")
                .createdBy("creator2")
                .build();

        // When & Then
        assertThatThrownBy(() -> userService.addUser(user))
                .hasRootCauseExactlyInstanceOf(JdbcSQLException.class)
                .hasStackTraceContaining("UPDATED_BY");
    }

    @Test
    @DisplayName("Add a null value")
    void addNullUser() {
        // Given

        // When & Then
        assertThatThrownBy(() -> userService.addUser(null))
                .hasRootCauseExactlyInstanceOf(IllegalArgumentException.class);
    }
}