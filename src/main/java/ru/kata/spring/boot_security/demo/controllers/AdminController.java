package ru.kata.spring.boot_security.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleServiceImpl roleService;

    public AdminController(UserService userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    @GetMapping("/create")
    public String addUserForm(@ModelAttribute User user) {
        return "adminCrudOperations/createUser";
    }


    @PostMapping("/create")
    public String addUser(@ModelAttribute @Valid User user,BindingResult bindingResult,
                          @RequestParam("role") String role, Model model) {
        user.setRole(role);
        if (!userService.saveUser(user)) {
            model.addAttribute("errorText", "Пользователь с таким именем уже существует");
            return "errorInfo";
        }

        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUserForm(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", roleService.findAll());
        return "adminCrudOperations/editUser";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("userForm") @Valid User user
            , BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("error",bindingResult);
            return "errorInfo";
        }

        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
