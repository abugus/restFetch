package ru.kata.spring.boot_security.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user",userService.getCurrentUser());
        return "admin";
    }


    @PostMapping("/create")
    public String addUser(@ModelAttribute @Valid User user, BindingResult bindingResult,
                          Model model) {
        Set<Role> roles = user.getRoles().stream()
                .map((Role name) -> roleService.findByName(name.getName()))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        if (!userService.saveUser(user)) {
            model.addAttribute("error", bindingResult);
            return "errorInfo";
        }
        return "redirect:/admin";
    }

    @PatchMapping("/edit")
    public String editUser(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult);
            return "errorInfo";
        }
        Set<Role> roles = user.getRoles().stream()
                .map(role -> roleService.findByName(role.getName()))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }


    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id, HttpServletRequest request,
                             HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = userService.getCurrentUser().getId();
        userService.deleteUser(id);
        if (currentUserId.equals(id)) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return "redirect:/login?logout";
        }
        return "redirect:/admin";
    }
}
