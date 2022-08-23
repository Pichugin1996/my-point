package com.weblinestudio.mypoint.validate;

import com.weblinestudio.mypoint.dto.UserRequestDto;
import com.weblinestudio.mypoint.entity.User;
import com.weblinestudio.mypoint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegisterFormValidator {

    private final UserService userService;
    private final ValidatorProperties properties;

    private UserRequestDto user;
    private Model model;
    private BindingResult bindingResult;

    @Autowired
    public RegisterFormValidator(UserService userService, ValidatorProperties properties) {
        this.userService = userService;
        this.properties = properties;
    }

    public void verify(UserRequestDto user, Model model, BindingResult bindingResult) {
        this.user = user;
        this.model = model;
        this.bindingResult = bindingResult;

        //Удаляем пробелы
        clear();

        checkUsername();
        checkPassword();
//        checkEmail();
//        checkFirstName();
//        checkLastName();
        checkPhoneNumber();

    }

    private void checkUsername() {
        if (checkLength(user.getUsername(),
                properties.getValidatorPatternLoginMin(),
                properties.getValidatorPatternLoginMax())) {
            bindingResult.rejectValue("username", "error.username",
                    properties.getValidatorMessageErrorUserLength());
            return;
        }
        if (!checkPattern(properties.getValidatorPatternUsername(), user.getUsername())) {
            bindingResult.rejectValue("username", "error.username",
                    properties.getValidatorMessageErrorUserPattern());
            return;
        }
        User userByUsername = userService.getUserByUsername(user.getUsername());
        if (userByUsername.getUsername() != null) {
            bindingResult.rejectValue("username", "error.username",
                    properties.getValidatorMessageErrorUserPattern());
        }
    }

    private void checkPassword() {
        if (checkLength(user.getPassword(),
                properties.getValidatorPatternPasswordMin(),
                properties.getValidatorPatternPasswordMax())) {
            bindingResult.rejectValue("password", "error.password",
                    properties.getValidatorMessageErrorPasswordLength());
        }
        if (!checkPattern(properties.getValidatorPatternPassword(), user.getPassword())) {
            bindingResult.rejectValue("password", "error.password",
                    properties.getValidatorMessageErrorPasswordPattern());
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            bindingResult.rejectValue("password", "error.password",
                    properties.getValidatorMessageErrorPasswordConfirm());
        }
    }

    //TODO сделать проверку на существование
    private void checkEmail() {

    }

    private void checkFirstName() {

    }

    private void checkLastName() {

    }

    private void checkPhoneNumber() {
        if (!checkPattern(properties.getValidatorPatternPhone(), user.getPhone())) {
            bindingResult.rejectValue("phone", "error.phone",
                    properties.getValidatorMessageErrorPhone());
        }
    }

    private boolean checkPattern(String regex, String obj) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(obj);
        return matcher.matches();
    }

    private boolean checkLength(String str, int minL, int maxL) {
        boolean error = false;
        if (str.length() >= maxL || str.length() <= minL) {
            error = true;
        }
        return error;
    }

    private void clear() {
        user.setUsername(user.getUsername().replaceAll("\\s+", ""));

        user.setPassword(user.getPassword().replaceAll("\\s+", ""));
        user.setPasswordConfirm(user.getPasswordConfirm().replaceAll("\\s+", ""));

        user.setEmail(user.getEmail().replaceAll("\\s+", ""));

        user.setFirstName(user.getFirstName().replaceAll("\\s+", ""));

        user.setLastName(user.getLastName().replaceAll("\\s+", ""));

        user.setPhone(user.getPhone().replaceAll("\\s+", ""));
    }
}
