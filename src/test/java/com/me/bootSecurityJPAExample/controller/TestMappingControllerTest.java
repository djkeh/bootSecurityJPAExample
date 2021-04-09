package com.me.bootSecurityJPAExample.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ActiveProfiles("test")
@WebMvcTest(controllers = TestMappingController.class, secure = false)
@ExtendWith(SpringExtension.class)
class TestMappingControllerTest {

    @Autowired private MockMvc mvc;


    @Test
    void test() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/test/test"))
                .andDo(print());
    }
}