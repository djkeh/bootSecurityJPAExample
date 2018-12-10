package com.me.bootSecurityJPAExample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.bootSecurityJPAExample.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Spring Data Rest API Tests")
public class SpringDataRestAPITests {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;

    @Test
    @DisplayName("[POST] Create a user")
    public void createUserTest() throws Exception {
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
    public void showUserTest() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[GET] Get an entire user list.")
    public void showUsersTest() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }
}
