package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/main")
    public String adminPage() {
        return "main";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }
}
