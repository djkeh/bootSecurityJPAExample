package com.me.bootSecurityJPAExample.service;

import com.me.bootSecurityJPAExample.annotation.TransactionalBasicTestAnnotations;
import com.me.bootSecurityJPAExample.domain.User;
import com.me.bootSecurityJPAExample.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.me.bootSecurityJPAExample.fixture.UserFixture.constructTestUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


@TransactionalBasicTestAnnotations
@DisplayName("User service logic tests")
class UserServiceTest {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @BeforeEach
    void before() {
        userRepository.deleteAll(); // TODO: To remove sample data from classpath:/resources/data.sql. This is to be removed when the sample data is removed.
    }


    @Test
    @DisplayName("Get User list containing single User")
    void getList() {
        // Given
        String loginId = "test";
        String password = "pw";
        userRepository.save(constructTestUser(loginId, password));

        // When
        List<User> list = userService.getList();

        // Then
        assertThat(list).hasSize(1);
        assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("loginId", loginId)
                .hasFieldOrPropertyWithValue("password", password);
    }

    @Test
    @DisplayName("Get a User")
    void getUser() {
        // Given
        String loginId = "test";
        String password = "pw";
        userRepository.save(constructTestUser(loginId, password));

        // When
        User user = userService.getUser(loginId);

        // Then
        assertThat(user)
                .hasFieldOrPropertyWithValue("loginId", loginId)
                .hasFieldOrPropertyWithValue("password", password);
    }

    @Test
    @DisplayName("Add a normal User")
    void addUser() {
        // Given
        User user = constructTestUser("test", "pw");

        // When
        userService.addUser(user);
        List<User> result = userRepository.findAll();

        // Then
        assertThat(result)
                .hasSize(1)
                .contains(user);
    }

    @Test
    @DisplayName("Check if the password encryption has done well")
    void checkAddedUserPassword() {
        // Given
        String loginId = "test";
        String password = "pw";
        User user = constructTestUser(loginId, password);

        // When
        userService.addUser(user);
        User result = userService.getUser(loginId);

        // Then
        assertThat(passwordEncoder.matches(password, result.getPassword())).isTrue();
        assertThat(result.getPassword()).contains("{bcrypt}");

    }

    @Test
    @DisplayName("(Exception) Add a User with a missing field")
    void addIncompleteUser() {
        // Given
        User user = constructTestUser(null, "pw2");

        // When
        Throwable thrown = catchThrowable(() -> userService.addUser(user));

        // Then
        assertThat(thrown)
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasStackTraceContaining("loginId");
    }

    @Test
    @DisplayName("(Exception) Add a null value")
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
    @DisplayName("(Exception) Add an existing user")
    void addExistingUser() {
        // Given
        String loginId = "test";
        String password = "pw";
        User user = constructTestUser(loginId, password);
        userRepository.save(constructTestUser(loginId, password));

        // When
        Throwable thrown = catchThrowable(() -> userService.addUser(user));

        // Then
        assertThat(thrown)
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasCauseExactlyInstanceOf(ConstraintViolationException.class)
                .hasStackTraceContaining("Unique index or primary key violation");
    }
}
