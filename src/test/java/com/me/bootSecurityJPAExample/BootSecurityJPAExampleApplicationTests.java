package com.me.bootSecurityJPAExample;

import com.me.bootSecurityJPAExample.annotation.BasicTestAnnotations;
import com.me.bootSecurityJPAExample.controller.MainController;
import com.me.bootSecurityJPAExample.repository.UserRepository;
import com.me.bootSecurityJPAExample.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


@BasicTestAnnotations
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
