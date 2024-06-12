package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRepository {
    void add(User user);

    User getByName(String username);

    User getById(Long id);

    void update(User user);

    void delete(long id);

    List<User> getUsersList();

    User getCurrentUser();
}
