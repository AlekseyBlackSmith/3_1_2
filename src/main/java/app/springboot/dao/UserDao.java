package app.springboot.dao;

import app.springboot.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    void removeUserById(Long id);
    void updateUser(User user);
    User getUserById(Long id);
    User getUserByEmailWithRoles(String name);
    List<User> getAllUsers();
    User getUserByIdWithRoles(Long id);
}
