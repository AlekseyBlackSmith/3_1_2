package app.springboot.service;

import app.springboot.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    void deleteById(Long id);
    User getById(Long id);
    User getByEmail(String email);
    List<User> listUsers();
}
