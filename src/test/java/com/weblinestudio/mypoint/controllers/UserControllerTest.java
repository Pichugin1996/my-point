package com.weblinestudio.mypoint.controllers;

import com.weblinestudio.mypoint.service.UserService;
import com.weblinestudio.mypoint.validate.RegisterFormValidator;
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
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-roles-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private RegisterFormValidator registerFormValidator;

    @Test
    void contextLoadsTest() {
        assertThat(userService).isNotNull();
        assertThat(registerFormValidator).isNotNull();
    }

    @Test
    void unauthenticatedLoginPage() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
                .andExpect(content().string(containsString("??2022 Web line studio.")))
                .andExpect(content().string(containsString("?????? ??????????:")))
                .andExpect(content().string(containsString("?????? ????????????:")))
                .andExpect(content().string(containsString("??????????")))
                .andExpect(content().string(containsString("????????????????????????????????????")));
    }

    @Test
    @WithUserDetails("User")
    void authenticatedLoginPage() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("??2022 Web line studio.")))
                .andExpect(content().string(containsString("???? ?????? ????????????????????????")))
                .andExpect(content().string(containsString("???? ?????????? ?????? User, ??????????")));
    }

    @Test
    void unauthenticatedRegistrationPage() throws Exception{
        mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
                .andExpect(content().string(containsString("??2022 Web line studio.")))
                .andExpect(content().string(containsString("??????????")))
                .andExpect(content().string(containsString("???????????????????????????????????? ?????? ???????????????? ???????????????")))
                .andExpect(content().string(containsString("???????????????????? ??????????:")))
                .andExpect(content().string(containsString("???????????????????? ????????????:")))
                .andExpect(content().string(containsString("??????????????????????????????????")));
    }

    @Test
    @WithUserDetails("User")
    void authenticatedRegistrationPage() throws Exception{
        mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(content().string(containsString("??2022 Web line studio.")))
                .andExpect(content().string(containsString("???? ?????????? ?????? User, ??????????")))
                .andExpect(content().string(containsString("???? ?????? ????????????????????????????????!")))
                .andExpect(content().string(containsString("??????????")));
    }

    @Test
    void correctRegistration() throws Exception{
        this.mockMvc.perform(post("/registration")
                        .param("username", "Username")
                        .param("password", "Password12345")
                        .param("passwordConfirm", "Password12345")
                        .param("email", "email-test@email.ru")
                        .param("firstName", "Alex")
                        .param("lastName", "Ivanov")
                        .param("phone", "89999999999")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    void correctRegistrationWithCheckBox() throws Exception{
        this.mockMvc.perform(post("/registration")
                        .param("username", "Username")
                        .param("password", "Password12345")
                        .param("passwordConfirm", "Password12345")
                        .param("email", "email-test@email.ru")
                        .param("firstName", "Alex")
                        .param("lastName", "Ivanov")
                        .param("phone", "89999999999")
                        .param("checkboxDirector", "true")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    void badRegistration() throws Exception{
        this.mockMvc.perform(post("/registration")
                        .param("username", "")
                        .param("password", "")
                        .param("passwordConfirm", "")
                        .param("email", "")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("phone", "")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("?????????? ???????????? ?????????????????? ???? 5 ???? 20 ????????????????!")))
                .andExpect(content().string(containsString("???????????? ???????????? ?????????????????? ???? 8 ???? 20 ????????????????!")))
                .andExpect(content().string(containsString("???????????? ???????????? ???????????????? ???? ???????????? ?????? ?????????? ???????????????? ????????????????????")))
                .andExpect(content().string(containsString("?????????? ???????????????? ???????????? ???? ??????????????????")));
    }
}






















