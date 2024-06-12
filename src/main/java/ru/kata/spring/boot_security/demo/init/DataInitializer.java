package ru.kata.spring.boot_security.demo.init;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Set;

@Component
public class DataInitializer {

    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataInitializer(UserService userService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @PostConstruct
    protected void CreateAdmin() {
        Role.setRole("ROLE_ADMIN", "ADMIN");
        Role.setRole("ROLE_USER", "USER");
        roleService.add(Role.getRole("ADMIN"));
        roleService.add(Role.getRole("USER"));
        userService.add(new User("admin", bCryptPasswordEncoder.encode("admin"), Set.of(Role.getRole("ADMIN"), Role.getRole("USER"))));
    }
}
