package app.springboot.service;

import app.springboot.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    void saveRole(Role role);
    Role getRoleById(long id);
    Role getRoleByName(String roleName);
}
