package ru.kata.spring.boot_security.demo.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    Role findByName(String name);

    void saveRole(Role role);

    List<Role> findAll();

    Role findRoleById(long id);

    void updateRole(Role updatedRole);

    void deleteRole(long id);
}
