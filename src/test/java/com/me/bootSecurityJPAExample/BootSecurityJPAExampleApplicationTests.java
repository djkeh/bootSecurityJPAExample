package com.me.bootSecurityJPAExample;

import com.me.bootSecurityJPAExample.controller.MainController;
import com.me.bootSecurityJPAExample.repository.UserRepository;
import com.me.bootSecurityJPAExample.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Boot, Security, JPA example application basic tests")
class BootSecurityJPAExampleApplicationTests {

    @Autowired private MainController mainController;
    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;

    @Test
    @DisplayName("Simple context loading test")
    void contextLoads() {
        // Given

        // When & Then
        assertThat(mainController).isNotNull();
        assertThat(userService).isNotNull();
        assertThat(userRepository).isNotNull();
    }

}
