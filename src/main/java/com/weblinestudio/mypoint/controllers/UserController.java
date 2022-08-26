package com.weblinestudio.mypoint.controllers;

import com.weblinestudio.mypoint.dto.UserRequestDto;
import com.weblinestudio.mypoint.service.UserService;
import com.weblinestudio.mypoint.validate.RegisterFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    private final RegisterFormValidator registerFormValidator;

    @Autowired
    public UserController(UserService userService, RegisterFormValidator registerFormValidator) {
        this.userService = userService;
        this.registerFormValidator = registerFormValidator;
    }

    @GetMapping("/login")
    public String loginPage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        log.debug("loginPage -- Get login page");
        return "user/login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        log.debug("registrationPage -- Get registration page");
        model.addAttribute("user", new UserRequestDto());
        log.debug("registrationPage -- Set attribute <user>: {}", model.getAttribute("user"));
        return "user/registration";
    }

    @PostMapping("/registration")
    public String registrationPagePost(@ModelAttribute("user") UserRequestDto user,
                                       Model model,
                                       BindingResult bindingResult,
                                       HttpServletRequest request) {
        log.debug("registrationPagePost");
        log.debug("registrationPagePost -- ModelAttribute 'user' : {}", user);
        registerFormValidator.verify(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/registration";
        }
        userService.createUser(user.transformUser());
        try {
            request.login(user.getUsername(), user.getPassword());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

    @GetMapping("/account")
    public String accountPage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "user/account";
    }
}





























