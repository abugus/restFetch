package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    @Transactional
    public boolean saveRole(Role role) {
        if ((roleRepository.findByName(role.getName())) == null) {
            roleRepository.saveRole(role);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleById(long id) {
        return roleRepository.findRoleById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void updateRole(Role updatedRole) {
        roleRepository.updateRole(updatedRole);
    }

    @Override
    @Transactional
    public void deleteRole(long id) {
        roleRepository.deleteRole(id);
    }
}
