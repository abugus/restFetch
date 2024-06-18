package ru.kata.spring.boot_security.demo.repositories;

import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;


@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private final EntityManager entityManager;

    public RoleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }
}
