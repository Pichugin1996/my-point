package com.weblinestudio.mypoint.controllers.business;

import com.weblinestudio.mypoint.service.CategoriesItemService;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql",
        "/create-item-before.sql",
        "/create-category-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-roles-after.sql",
        "/create-user-after.sql",
        "/create-item-after.sql",
        "/create-category-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CategoriesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoriesItemService categoriesItemService;

    @Test
    void contextLoadsTest() {
        assertThat(categoriesItemService).isNotNull();
    }

    @Test
    void unauthenticatedCategoriesPage() throws Exception {
        this.mockMvc.perform(get("/storage/categories"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithUserDetails("User")
    void authenticatedCategoriesPage() throws Exception {
        this.mockMvc.perform(get("/storage/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Напитки")))
                .andExpect(content().string(containsString("Хозяйственные товары")))
                .andExpect(content().string(containsString("Вы вошли как User, Выйти")));
    }

    @Test
    @WithUserDetails("User")
    void createNewCategoryCorrectTest() throws Exception {
        this.mockMvc.perform(post("/storage/categories/edit")
                        .param("categoriesName", "Молочные продукты")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Молочные продукты")));
        this.mockMvc.perform(get("/storage/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Молочные продукты")));
    }

    @Test
    @WithUserDetails("User")
    void deleteCategoryTest() throws Exception {
        mockMvc.perform(post("/storage/categories/edit/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @WithUserDetails("User")
    void editCategoryTest() throws Exception {
        this.mockMvc.perform(post("/storage/categories/edit")
                        .param("categoriesName", "Молочные продукты")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Молочные продукты")));
        mockMvc.perform(get("/storage/categories/edit/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Молочные продукты")));
        this.mockMvc.perform(post("/storage/categories/edit")
                        .param("id", "1")
                        .param("status", "ACTIVE")
                        .param("userId", "10")
                        .param("categoriesName", "Колбасные продукты")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Колбасные продукты")));
        this.mockMvc.perform(get("/storage/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Колбасные продукты")));
    }

    @Test
    @WithUserDetails("User")
    void CategoryEditPage() throws Exception {
        this.mockMvc.perform(get("/storage/categories/edit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Наименование")));
    }


}