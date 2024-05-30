package ru.kata.spring.boot_security.demo.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Collection;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByUsername(String username) {
        try {
            return entityManager.createQuery("FROM User WHERE email = :name", User.class)
                    .setParameter("name", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery
                ("from User", User.class).getResultList();
    }

    @Override
    public User findUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User userToUpdate) {
        entityManager.merge(userToUpdate);
    }

    @Override
    public void deleteUser(long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return (User) userDetails;
        }
        return null;
    }
}
