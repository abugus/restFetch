package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.util.Arrays;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserServiceImpl userServiceImpl;
    private final SuccessUserHandler successUserHandler;
    private final BCryptPasswordEncoder passwordEncoder;

    public WebSecurityConfig(UserServiceImpl userServiceImpl, SuccessUserHandler successUserHandler, BCryptPasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.successUserHandler = successUserHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/registration").not().authenticated()
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .permitAll();

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public CommandLineRunner initData(UserService userService, RoleService roleService) {
        return args -> {
            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");
            roleService.saveRole(userRole);
            roleService.saveRole(adminRole);

            User admin = new User("admin", "password");
            admin.setRole("admin");
            userService.saveUser(admin);

            User user = new User("user", "password");
            user.setRole("user");
            userService.saveUser(user);
        };
    }

}
