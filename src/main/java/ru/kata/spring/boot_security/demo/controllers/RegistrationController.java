package ru.kata.spring.boot_security.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;
    private final RoleService roleService;

    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String registrationForm(Model model) {
        model.addAttribute("roles",roleService.findAll());
        return "registration";
    }

    @PostMapping
    public String registrationForm(@ModelAttribute("userForm") @Valid User user
            , BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult);
            return "errorInfo";
        }
        Set<Role> roles = user.getRoles().stream()
                .map((Role name) -> roleService.findByName(name.getName()))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        if (!userService.saveUser(user)) {
            System.out.println("не удалось сохранить пользователя");
        }
        return "redirect:/login";
    }

}
