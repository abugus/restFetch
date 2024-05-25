package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
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
    public void updateUser(User userToUpdate) {
        User user = findByUsername(userToUpdate.getUsername());
        Collection<Role> roles = new ArrayList<>();
        for (Role role : userToUpdate.getRoles()) {
            Role role1 = roleService.findByName(role.getName());
            roleService.saveRole(role1);
            roles.add(role1);
        }
        user.getRoles().clear();
        user.setRoles(roles);
        userRepository.updateUser(user);
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
        Collection<Role> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            Role role1 = roleService.findByName(role.getName());
            if (role1 == null) {
                role1 = new Role(role.getName());
            }
            roleService.saveRole(role1);
            roles.add(role1);
        }
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }
}
