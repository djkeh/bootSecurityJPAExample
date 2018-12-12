package com.me.bootSecurityJPAExample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.bootSecurityJPAExample.domain.User;
import com.me.bootSecurityJPAExample.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Main controller tests")
class MainControllerTest {

    @Autowired private WebApplicationContext context;
    @Autowired private ObjectMapper mapper;
    @MockBean private UserService userService; // TODO: @MockBean marks the cache of Spring context dirty, thereby slows down overall tests.

    private MockMvc mvc;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser("test")
    @DisplayName("[GET] main view")
    void main() throws Exception {
        // Given
        User user = User.builder().build();
        List<User> list = new ArrayList<>();
        list.add(user);
        given(userService.getList()).willReturn(list);
        given(userService.getUser(any())).willReturn(user);

        // When & Then
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Java + Spring Boot 2 + Security + JPA Example")))
                .andExpect(content().string(containsString("사용자 리스트")))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("myAccount"));
        verify(userService).getList();
        verify(userService).getUser(any());
    }

    @Test
    @WithMockUser("test")
    @DisplayName("[POST] add a user")
    void formMain() throws Exception {
        // Given
        User user = User.builder().build();
        List<User> list = new ArrayList<>();
        list.add(user);
        String body = mapper.writeValueAsString(user);
        given(userService.getList()).willReturn(list);
        given(userService.getUser(any())).willReturn(user);
        willDoNothing().given(userService).addUser(any());


        // When & Then
        mvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Java + Spring Boot 2 + Security + JPA Example")))
                .andExpect(content().string(containsString("사용자 리스트")))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("myAccount"));
        verify(userService).getList();
        verify(userService).getUser(any());
        verify(userService).addUser(any());
    }
}
