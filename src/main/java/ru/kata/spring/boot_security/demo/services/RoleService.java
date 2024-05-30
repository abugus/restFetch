package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    Role findByName(String name);

    boolean saveRole(Role role);

    List<Role> findAll();

    Role findRoleById(long id);

    void updateRole(Role updatedRole);

    void deleteRole(long id);
}
