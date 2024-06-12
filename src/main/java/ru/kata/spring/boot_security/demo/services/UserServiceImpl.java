package ru.kata.spring.boot_security.demo.services;

import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void add(User user) {
        userRepository.add(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Transactional
    @Override
    public void delete(long id) {
        try {
            userRepository.delete(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Введены неверные данные", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(long id) {
        User user = userRepository.getById(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsersList() {
        return userRepository.getUsersList();
    }

    @Transactional()
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        Hibernate.initialize(user.getRoleSet());
        return user;
    }


    @Transactional
    @Override
    public void usernameCheck(User user) {
        if (userRepository.getByName(user.getUsername()) != null) {
            throw new IllegalArgumentException("Пользователь с таким именем существует");
        }
    }

    @Override
    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }

}