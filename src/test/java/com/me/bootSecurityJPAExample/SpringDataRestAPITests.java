package com.me.bootSecurityJPAExample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.bootSecurityJPAExample.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Spring Data Rest API Tests")
class SpringDataRestAPITests {

    @Autowired private WebApplicationContext context;
    @Autowired private ObjectMapper mapper;

    private MockMvc mvc;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("[POST] Create a user")
    void createUserTest() throws Exception {
        // Given
        User user = User.builder()
                .loginId("test")
                .password("password")
                .createdBy("creator")
                .updatedBy("creator")
                .build();
        String body = mapper.writeValueAsString(user);

        // When & Then
        mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("[GET] Get a user info.")
    void showUserTest() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[GET] Get an entire user list.")
    void showUsersTest() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }
}
