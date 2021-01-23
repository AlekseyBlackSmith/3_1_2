package app.springboot.service;

import app.springboot.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    void removeById(Long id);
    User getById(Long id);
    User getByUserName(String userName);
    List<User> listUsers();
}
