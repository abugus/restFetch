package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRepository {
    User findByUsername(String username);

    void save(User user);

    List<User> findAll();

    User findUserById(long id);

    void updateUser(User updatedUser);

    void deleteUser(long id);
}
