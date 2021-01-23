package app.springboot.service;

import app.springboot.model.User;
import app.springboot.repository.RoleRepository;
import app.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.getByUserName(userName);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }


//    private UserDao userDao;
//
//    @Autowired
//    public UserServiceImpl(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    @Override
//    @Transactional
//    public void save(User user) {
//        userDao.save(user);
//    }
//
//    @Override
//    @Transactional
//    public void removeById(Long id) {
//        userDao.removeById(id);
//    }
//
//
//    @Override
//    @Transactional
//    public User getById(Long id) {
//        return userDao.getById(id);
//    }
//
//    @Override
//    @Transactional
//    public User getByUserName(String name) {
//        return userDao.getByUserName(name);
//    }
//
//    @Override
//    @Transactional
//    public List<User> listUsers() {
//        return userDao.listUsers();
//    }

}
