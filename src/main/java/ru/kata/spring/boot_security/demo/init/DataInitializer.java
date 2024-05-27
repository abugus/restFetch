package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(UserService userService, RoleService roleService) {
        return args -> {
            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");
            roleService.saveRole(userRole);
            roleService.saveRole(adminRole);

            User admin = new User("admin", "password");
            admin.addRole(adminRole);
            userService.saveUser(admin);

            User user = new User("user", "password");
            user.addRole(userRole);
            userService.saveUser(user);
        };
    }
}
