package com.weblinestudio.mypoint.controllers.business;

import com.weblinestudio.mypoint.service.CategoriesItemService;
import com.weblinestudio.mypoint.service.ItemsService;
import com.weblinestudio.mypoint.validate.ItemFormValidator;
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
class ItemsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private CategoriesItemService categoriesItemService;
    @Autowired
    private ItemFormValidator itemFormValidator;

    @Test
    void contextLoadsTest() {
        assertThat(itemsService).isNotNull();
        assertThat(categoriesItemService).isNotNull();
        assertThat(itemFormValidator).isNotNull();
    }

    @Test
    void unauthenticatedItemsPageTest() throws Exception {
        this.mockMvc.perform(get("/storage/items"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithUserDetails("User")
    void authenticatedItemsPageTest() throws Exception {
        this.mockMvc.perform(get("/storage/items"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Наименование")))
                .andExpect(content().string(containsString("Категория")))
                .andExpect(content().string(containsString("Категория")))
                .andExpect(content().string(containsString("105.10")));
    }

    @Test
    @WithUserDetails("User")
    void createNewItemTest() throws Exception {
        this.mockMvc.perform(post("/storage/edit")
                        .param("titleName", "Cок Злой апельсиновый 0.3")
                        .param("category", "Напитки")
                        .param("price", "100")
                        .param("amount", "0")
                        .param("weight", "0.3")
                        .param("creationDate", "")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Cок Злой апельсиновый 0.3")));
        this.mockMvc.perform(get("/storage/items"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Cок Злой апельсиновый 0.3")));
    }

    @Test
    @WithUserDetails("User")
    void deleteCategoryTest() throws Exception {
        mockMvc.perform(post("/storage/delete/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @WithUserDetails("User")
    void CategoryEditPage() throws Exception {
        this.mockMvc.perform(get("/storage/edit/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Наименование")))
                .andExpect(content().string(containsString("Категория")));
    }

    @Test
    @WithUserDetails("User")
    void CategoryEditPageTest() throws Exception {
        this.mockMvc.perform(get("/storage/edit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("Наименование")))
                .andExpect(content().string(containsString("Категория")));
    }

}