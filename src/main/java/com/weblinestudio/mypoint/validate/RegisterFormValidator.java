package com.weblinestudio.mypoint.validate;

import com.weblinestudio.mypoint.dto.UserRequestDto;
import com.weblinestudio.mypoint.entity.User;
import com.weblinestudio.mypoint.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class RegisterFormValidator {

    private final UserService userService;
    private final ValidatorProperties properties;

    private UserRequestDto user;
    private BindingResult bindingResult;

    private boolean error = false;


    @Autowired
    public RegisterFormValidator(UserService userService, ValidatorProperties properties) {
        this.userService = userService;
        this.properties = properties;
    }

    public void verify(UserRequestDto user, BindingResult bindingResult) {
        error = false;
        log.debug("verify -- start user: {}", user);
        this.user = user;
        this.bindingResult = bindingResult;

        //Удаляем пробелы
        clear();

        checkUsername();
        checkPassword();
        checkEmail();
//        checkFirstName();
//        checkLastName();
        checkPhoneNumber();

    }

    private void checkUsername() {
        log.debug("checkUsername -- start");
        if (checkLength(user.getUsername(),
                properties.getValidatorPatternLoginMin(),
                properties.getValidatorPatternLoginMax())) {
            bindingResult.rejectValue("username", "error.username",
                    properties.getValidatorMessageErrorUserLength());
            error = true;
            log.debug("checkUsername -- checkLength:error user: {}", user);
            return;
        }
        if (!checkPattern(properties.getValidatorPatternUsername(), user.getUsername())) {
            bindingResult.rejectValue("username", "error.username",
                    properties.getValidatorMessageErrorUserPattern());
            error = true;
            log.debug("checkUsername -- checkPattern:error user: {}", user);
            return;
        }
        User userByUsername = userService.getUserByUsername(user.getUsername());
        if (userByUsername.getUsername() != null) {
            bindingResult.rejectValue("username", "error.username",
                    properties.getValidatorMessageErrorUserPattern());
            error = true;
            log.debug("checkUsername -- checkUserIsExist?:error user: {}", user);
        }
    }

    private void checkPassword() {
        log.debug("checkPassword -- start");
        if (checkLength(user.getPassword(),
                properties.getValidatorPatternPasswordMin(),
                properties.getValidatorPatternPasswordMax())) {
            bindingResult.rejectValue("password", "error.password",
                    properties.getValidatorMessageErrorPasswordLength());
            log.debug("checkPassword -- checkLength:error user: {}", user);
            error = true;
        }
        if (!checkPattern(properties.getValidatorPatternPassword(), user.getPassword())) {
            bindingResult.rejectValue("password", "error.password",
                    properties.getValidatorMessageErrorPasswordPattern());
            log.debug("checkPassword -- checkPattern:error user: {}", user);
            error = true;
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            bindingResult.rejectValue("password", "error.password",
                    properties.getValidatorMessageErrorPasswordConfirm());
            error = true;
            log.debug("checkPassword -- checkPasswordConfirm:error user: {}", user);
        }
    }

    //TODO сделать проверку на существование
    private void checkEmail() {
        log.debug("checkEmail -- start");
        if (userService.getUserByEmail(user.getEmail()).getEmail() != null) {
            bindingResult.rejectValue("email", "error.email",
                    properties.getValidatorMessageErrorEmailExist());
            error = true;
            log.debug("checkEmail -- checkEmailIsExist:error user: {}", user);
        }
    }

    private void checkFirstName() {

    }

    private void checkLastName() {

    }

    private void checkPhoneNumber() {
        log.debug("checkPhoneNumber -- start");
        if (!checkPattern(properties.getValidatorPatternPhone(), user.getPhone())) {
            bindingResult.rejectValue("phone", "error.phone",
                    properties.getValidatorMessageErrorPhone());
            error = true;
            log.debug("checkPhoneNumber -- checkPattern:error user: {}", user);
            return;
        }
        if(userService.getUserByPhone(user.getPhone()).getPhone() != null){
            bindingResult.rejectValue("phone", "error.phone",
                    properties.getValidatorMessageErrorPhoneExist());
            error = true;
            log.debug("checkPhoneNumber -- checkExist:error user: {}", user);
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
        log.debug("clear -- start");
        user.setUsername(user.getUsername().replaceAll("\\s+", ""));

        user.setPassword(user.getPassword().replaceAll("\\s+", ""));
        user.setPasswordConfirm(user.getPasswordConfirm().replaceAll("\\s+", ""));

        user.setEmail(user.getEmail().replaceAll("\\s+", ""));

        user.setFirstName(user.getFirstName().replaceAll("\\s+", ""));

        user.setLastName(user.getLastName().replaceAll("\\s+", ""));

        user.setPhone(user.getPhone().replaceAll("\\s+", ""));
        log.debug("clear -- end user: {}", user);
    }

    public boolean isError() {
        return error;
    }
}
