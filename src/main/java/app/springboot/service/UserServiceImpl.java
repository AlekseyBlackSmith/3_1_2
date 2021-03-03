package app.springboot.service;

import app.springboot.model.User;
import app.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User getById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
