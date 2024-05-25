package ru.kata.spring.boot_security.demo.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role findByName(String name) {
        try {
            return entityManager.createQuery("FROM Role WHERE name = :roleName",
                    Role.class).setParameter("roleName", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery
                ("from Role", Role.class).getResultList();
    }

    @Override
    public Optional<Role> findRoleById(long id) {
        return Optional.ofNullable(entityManager.find(Role.class, id));
    }

    @Override
    public void updateRole(Role updatedRole) {
        entityManager.merge(updatedRole);
    }

    @Override
    public void deleteRole(long id) {
        entityManager.remove(id);
    }
}
