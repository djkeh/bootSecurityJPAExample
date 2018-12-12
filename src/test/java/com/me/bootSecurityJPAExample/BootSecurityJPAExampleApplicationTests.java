package com.me.bootSecurityJPAExample;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Boot, Security, JPA example application basic tests")
class BootSecurityJPAExampleApplicationTests {

    @Test
    @DisplayName("Context loading")
    void contextLoads() {
    }

}
