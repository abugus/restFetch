package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.Set;

public interface RoleRepository {
    void add(Role role);

    Role findByName(String roleName);
}
