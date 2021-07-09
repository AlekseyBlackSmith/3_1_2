package app.springboot.dao;


import app.springboot.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
    return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
}

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role getRoleById(long roleId) {
        return entityManager.find(Role.class, roleId);
    }


    @Override
    public Role getRoleByName(String roleName) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.roleName = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getSingleResult();
    }
}
