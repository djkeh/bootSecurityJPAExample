package com.me.bootSecurityJPAExample.service;

import com.me.bootSecurityJPAExample.domain.User;
import com.me.bootSecurityJPAExample.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("User service logic tests")
class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

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
    @DisplayName("[[TEST]]")
    void test() {
        userRepository.findByNicknameAndEmailAndDescription("Uno", null, "bla bla bla");
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
    @DisplayName("Check if the password encryption has done well")
    void checkAddedUserPassword() {
        // Given
        String loginId = "test2";
        String password = "password2";
        User user = User.builder()
                .loginId(loginId)
                .password(password)
                .createdBy("creator")
                .updatedBy("creator")
                .build();

        // When
        userService.addUser(user);
        User result = userService.getUser(loginId);

        // Then
        assertThat(passwordEncoder.matches(password, result.getPassword())).isTrue();
        assertThat(result.getPassword()).contains("{bcrypt}");

    }

    @Test
    @DisplayName("Add a User with a missing field")
    void addIncompleteUser() {
        // Given
        User user = User.builder()
                .password("password")
                .createdBy("creator2")
                .updatedBy("creator2")
                .build();

        // When
        Throwable thrown = catchThrowable(() -> userService.addUser(user));

        // Then
        assertThat(thrown)
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasStackTraceContaining("LOGIN_ID");
    }

    @Test
    @DisplayName("Add a null value")
    void addNullUser() {
        // Given

        // When
        Throwable thrown = catchThrowable(() -> userService.addUser(null));

        // Then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null");
    }

    @Test
    @DisplayName("Add an existing user")
    void addExistingUser() {
        // Given
        User user = User.builder()
                .loginId("test")
                .password("password")
                .createdBy("creator")
                .updatedBy("creator")
                .build();

        // When
        Throwable thrown = catchThrowable(() -> userService.addUser(user));

        // Then
        assertThat(thrown)
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasCauseExactlyInstanceOf(ConstraintViolationException.class)
                .hasStackTraceContaining("Unique index or primary key violation");
    }
}
