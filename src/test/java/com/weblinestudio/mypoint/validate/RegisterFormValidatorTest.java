package com.weblinestudio.mypoint.validate;


import com.weblinestudio.mypoint.dto.UserRequestDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RegisterFormValidatorTest {

    @Autowired
    private RegisterFormValidator registerFormValidator;

    private UserRequestDto userRequestDto;

    @MockBean
    private BindingResult bindingResult;

    @Test
    void contextLoadsTest() {
        assertThat(registerFormValidator).isNotNull();
    }

    @Test
    void correctUser() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setUsername("Testers_12-3");
        userRequestDto.setPassword("Password123");
        userRequestDto.setPasswordConfirm("Password123");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999999");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(false, registerFormValidator.isError());
    }

    @Test
    void badUsernameUser1() {
        userRequestDto = new UserRequestDto();
        //test
        userRequestDto.setUsername("");

        //correct
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setPassword("Password123");
        userRequestDto.setPasswordConfirm("Password123");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999999");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void badUsernameUser2() {
        userRequestDto = new UserRequestDto();
        //test
        userRequestDto.setUsername("longggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        //correct
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setPassword("Password123");
        userRequestDto.setPasswordConfirm("Password123");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999999");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void badUsernameUser3() {
        userRequestDto = new UserRequestDto();
        //test
        userRequestDto.setUsername("     ");

        //correct
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setPassword("Password123");
        userRequestDto.setPasswordConfirm("Password123");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999999");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void badUsernameUser4() {
        userRequestDto = new UserRequestDto();
        //test
        userRequestDto.setUsername("sml");

        //correct
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setPassword("Password123");
        userRequestDto.setPasswordConfirm("Password123");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999999");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void badUsernameUser5() {
        userRequestDto = new UserRequestDto();
        //test
        userRequestDto.setUsername("     ");

        //correct
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setPassword("Password123");
        userRequestDto.setPasswordConfirm("Password123");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999999");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void badUsernameUser6() {
        userRequestDto = new UserRequestDto();
        //test
        userRequestDto.setUsername("********");

        //correct
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setPassword("Password123");
        userRequestDto.setPasswordConfirm("Password123");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999999");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void badPassword1() {
        userRequestDto = new UserRequestDto();
        //test
        userRequestDto.setPassword("Pass");
        userRequestDto.setPasswordConfirm("Pass");

        //correct
        userRequestDto.setUsername("Testers_12-3");
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999999");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void badPassword2() {
        userRequestDto = new UserRequestDto();
        //test
        userRequestDto.setPassword("Passwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        userRequestDto.setPasswordConfirm("Passwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");

        //correct
        userRequestDto.setUsername("Testers_12-3");
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999999");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void badPassword3() {
        userRequestDto = new UserRequestDto();
        //test
        userRequestDto.setPassword("Password123");
        userRequestDto.setPasswordConfirm("Password12");

        //correct
        userRequestDto.setUsername("Testers_12-3");
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999999");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void goodPassword4() {
        userRequestDto = new UserRequestDto();
        //test
        userRequestDto.setPassword("Password123/*-_");
        userRequestDto.setPasswordConfirm("Password123/*-_");

        //correct
        userRequestDto.setUsername("Testers_12-3");
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999999");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(false, registerFormValidator.isError());
    }

    @Test
    void goodPhone1() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setPhone("89999999999");
        //test

        //correct
        userRequestDto.setPassword("Password123/*-_");
        userRequestDto.setPasswordConfirm("Password123/*-_");
        userRequestDto.setUsername("Testers_12-3");
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(false, registerFormValidator.isError());
    }

    @Test
    void badPhone2() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setPhone("   ");
        //test

        //correct
        userRequestDto.setPassword("Password123/*-_");
        userRequestDto.setPasswordConfirm("Password123/*-_");
        userRequestDto.setUsername("Testers_12-3");
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void badPhone3() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setPhone("895");
        //test

        //correct
        userRequestDto.setPassword("Password123/*-_");
        userRequestDto.setPasswordConfirm("Password123/*-_");
        userRequestDto.setUsername("Testers_12-3");
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void badPhone4() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setPhone("999999999999999999999999999999");
        //test

        //correct
        userRequestDto.setPassword("Password123/*-_");
        userRequestDto.setPasswordConfirm("Password123/*-_");
        userRequestDto.setUsername("Testers_12-3");
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void badPhone5() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setPhone("qwertyuiop");
        //test

        //correct
        userRequestDto.setPassword("Password123/*-_");
        userRequestDto.setPasswordConfirm("Password123/*-_");
        userRequestDto.setUsername("Testers_12-3");
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void checkUsernameExist() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("UserNameTest");

        userRequestDto.setEmail("emailNameTest@mail.com");
        userRequestDto.setPassword("Password123");
        userRequestDto.setPasswordConfirm("Password123");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999970");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }


    @Test
    void checkEmailExist() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("email@email.ru");

        userRequestDto.setUsername("Testers_12-3");
        userRequestDto.setPassword("Password123");
        userRequestDto.setPasswordConfirm("Password123");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setPhone("89999999999");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }

    @Test
    void checkPhoneExist() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setPhone("89998488888");

        userRequestDto.setUsername("Testers_12-3");
        userRequestDto.setEmail("emailtester@mail.com");
        userRequestDto.setPassword("Password123");
        userRequestDto.setPasswordConfirm("Password123");
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Flomasters");
        userRequestDto.setCheckboxDirector(true);

        registerFormValidator.verify(userRequestDto, bindingResult);
        Assert.assertEquals(true, registerFormValidator.isError());
    }



}