package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public boolean updateUser(User userToUpdate) {
        User userFromDB = findByUsername(userToUpdate.getUsername());
        if(userFromDB==null){
            return false;
        }
        userFromDB.setPassword(bCryptPasswordEncoder.encode(userToUpdate.getPassword()));
        userFromDB.setRoles(userToUpdate.getRoles());
        userRepository.updateUser(userFromDB);
        return true;
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        if (userRepository.findUserById(id) != null) {
            userRepository.deleteUser(id);
        }
    }

    @Override
    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    @Override
    public User findUserById(Long userId) {
        try {
            return userRepository.findUserById(userId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}
