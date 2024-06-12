package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;




public interface UserService extends UserDetailsService {


    void add(User user);

    void update(User user);

    void delete(long id);

    User getUser(long id);

    List<User> getUsersList();

    void usernameCheck(User user);
    User getCurrentUser();
}
