package app.springboot.dao;


import app.springboot.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void removeUserById(Long userId) {
        entityManager.remove(entityManager.find(User.class, userId));
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserById(Long userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public User getUserByEmailWithRoles(String email) {
    return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email", User.class)
            .setParameter("email", email)
            .getSingleResult();
}

    @Override
    public List<User> getAllUsers() {
    return entityManager.createQuery("SELECT u FROM User u", User.class)
            .getResultList();
}

    @Override
    public User getUserByIdWithRoles(Long id) {
        return entityManager.createQuery("SELECT DISTINCT u FROM User u JOIN FETCH u.roles WHERE u.userId = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
