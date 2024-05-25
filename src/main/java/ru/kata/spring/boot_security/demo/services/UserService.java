package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    List<User> findAll();

    User findUserById(Long userId);

    void updateUser(User updatedUser);

    boolean saveUser(User user);

    void deleteUser(long id);

    User getCurrentUser();

}
