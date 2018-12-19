package com.me.bootSecurityJPAExample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.bootSecurityJPAExample.annotation.TransactionalBasicTestAnnotations;
import com.me.bootSecurityJPAExample.domain.User;
import com.me.bootSecurityJPAExample.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TransactionalBasicTestAnnotations
@DisplayName("Spring Data Rest API Tests")
class SpringDataRestAPITests {

    @Autowired private WebApplicationContext context;
    @Autowired private UserRepository userRepository;
    @Autowired private ObjectMapper mapper;

    private MockMvc mvc;
    private String baseUrl;
    private String searchUrl;

    @BeforeEach
    void before() {
        baseUrl = "/api/users";
        searchUrl = baseUrl + "/search";
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Nested
    @TransactionalBasicTestAnnotations
    @DisplayName("for basic CRUD APIs")
    class ForBasicAPIs {
        @Test
        @DisplayName("[POST] Create a user")
        void createUserTest() throws Exception {
            // Given
            User user = constructTestUser("newUser", "pw");
            String body = mapper.writeValueAsString(user);

            // When & Then
            mvc.perform(
                    post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body)
            )
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("[GET] Get a user info.")
        void showUserTest() throws Exception {
            // Given
            User savedUser = userRepository.save(constructTestUser("test", "pw"));
            long id = savedUser.getId();

            // When & Then
            mvc.perform(get(baseUrl + "/" + id))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("[GET] Get an entire user list.")
        void showUsersTest() throws Exception {
            // Given

            // When & Then
            mvc.perform(get(baseUrl))
                    .andExpect(status().isOk());
        }
    }


    @Nested
    @TransactionalBasicTestAnnotations
    @DisplayName("for user-custom(search) APIs")
    class ForSearchAPIs {
        @Test
        @DisplayName("[GET] Search a user with login_id")
        void showUsersTest() throws Exception {
            // Given
            String loginId = "testId";
            String password = "testPassword";
            User user = constructTestUser(loginId, password);
            userRepository.save(user);

            // When & Then
            mvc.perform(
                    get(searchUrl + "/findByLoginId")
                            .param("loginId", loginId)
            )
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(password)));
        }

        @Test
        @DisplayName("[GET] Check if a user with login_id exists")
        void checkIfExists() throws Exception {
            // Given
            String loginId = "testId";
            User user = constructTestUser(loginId, "pw");
            userRepository.save(user);

            // When & Then
            mvc.perform(
                    get(searchUrl + "/existsByLoginId")
                            .param("loginId", loginId)
            )
                    .andExpect(status().isOk())
                    .andExpect(content().string(is(Boolean.TRUE.toString())));
        }
    }


    // Stubs
    private User constructTestUser(String loginId, String password) {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .createdBy("creator")
                .updatedBy("creator")
                .build();
    }
}
