package com.me.bootSecurityJPAExample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.bootSecurityJPAExample.annotation.BasicTestAnnotations;
import com.me.bootSecurityJPAExample.domain.User;
import com.me.bootSecurityJPAExample.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@BasicTestAnnotations
@DisplayName("Main controller tests")
class MainControllerTest {

    @Autowired private MainController mainController;
    @Autowired private Filter springSecurityFilterChain;
    @Autowired private ObjectMapper mapper;
    @MockBean private UserService userService; // TODO: @MockBean marks the cache of Spring context dirty, thereby slows down overall tests.

    private MockMvc mvc;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders
                .standaloneSetup(mainController)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    @DisplayName("[GET] main view")
    void main() throws Exception {
        // Given
        User user = User.builder().build();
        List<User> list = new ArrayList<>();
        list.add(user);
        given(userService.getList()).willReturn(list);
        given(userService.getUser(any())).willReturn(user);

        // When & Then
        mvc.perform(
                get("/")
                        .with(user("user"))
        )
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("myAccount"));
        verify(userService).getList();
        verify(userService).getUser(any());
    }

    @Test
    @DisplayName("[GET] main view without authentication")
    void mainWithoutLogin() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
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
        mvc.perform(
                post("/")
                        .with(user("user"))
                        .content(body)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("myAccount"));
        verify(userService).getList();
        verify(userService).getUser(any());
        verify(userService).addUser(any());
    }

    @Test
    @DisplayName("[POST] add a user without authentication")
    void formMainWithoutLogin() throws Exception {
        // Given
        User user = User.builder().build();
        String body = mapper.writeValueAsString(user);

        // When & Then
        mvc.perform(
                post("/")
                        .content(body)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/login"));
    }
}
