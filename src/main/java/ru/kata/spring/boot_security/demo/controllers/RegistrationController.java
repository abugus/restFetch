package ru.kata.spring.boot_security.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registrationForm() {
        return "registration";
    }

    @PostMapping
    public String registrationForm(@ModelAttribute @Valid User user, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error",bindingResult);
            return "errorInfo";
        }
        if (!userService.saveUser(user)) {
            System.out.println("не удалось сохранить пользователя");
        }
        return "redirect:/";
    }
//    @PostMapping
//    public String registrationForm(@RequestParam("username") String username,
//                                   @RequestParam("password") String password,
//                                   @RequestParam("role") String role) {
//        User user = new User(username, password);
//        user.setRole(role);
//        if(!userService.saveUser(user)){
//            System.out.println("не удалось сохранить пользователя");
//        }
//
//        return "redirect:/";
//    }

}
