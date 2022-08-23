package com.weblinestudio.mypoint.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@Slf4j
public class WebController {

    @GetMapping("/")
    public String mainPage(Model model, Principal principal) {
        log.info("mainPage -- Get main page");
        if (principal != null) {
            log.debug("mainPage -- set username: {}", principal.getName());
            model.addAttribute("username", principal.getName());
        }
        return "/main";
    }
}
