package com.weblinestudio.mypoint.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class StorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StorageController storageController;

    @Test
    void contextLoadsTest() {
        assertThat(storageController).isNotNull();
    }

    @Test
    @WithUserDetails("User")
    void HomePageTest() throws Exception {
        this.mockMvc.perform(get("/storage"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("©2022 Web line studio.")))
                .andExpect(content().string(containsString("Товары")))
                .andExpect(content().string(containsString("Финансы")))
                .andExpect(content().string(containsString("Категории")));
    }

    @Test
    @WithUserDetails("User")
    void authenticatedHomePageTest() throws Exception {
        this.mockMvc.perform(get("/storage"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("User")));
    }

    @Test
    void unauthenticatedHomePageTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
                .andExpect(content().string(containsString("Войти")))
                .andExpect(content().string(containsString("Зарегистрироваться")));

    }
}