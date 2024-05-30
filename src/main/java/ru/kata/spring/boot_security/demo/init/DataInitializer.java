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

            User admin = new User( "admin","admin",21,"admin@mail.ru","password");
            admin.addRole(roleService.findByName(adminRole.getName()));
            userService.saveUser(admin);

            User user = new User("user","user",21,"user@mail.ru","password");
            user.addRole(roleService.findByName(userRole.getName()));
            userService.saveUser(user);
        };
    }
}
