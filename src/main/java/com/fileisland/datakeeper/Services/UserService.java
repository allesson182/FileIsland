package com.fileisland.datakeeper.Services;

import com.fileisland.datakeeper.Dao.Entity.User;
import com.fileisland.datakeeper.Dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;


    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    public User findById(Long id){
        return userDao.findById(id).orElse(null);
    }
    public User save(User user){
        return userDao.save(user);
    }
    public void deleteById(Long id){
        userDao.deleteById(id);
    }
    public void delete(User user){
        userDao.delete(user);
    }
    public User updateEmail(Long userId, String email){
        User existingUser = userDao.findById(userId).orElse(null);

        if (existingUser == null || email == null || email.isEmpty())
            throw new IllegalArgumentException("User not found or email is empty");

        existingUser.setEmail(email);
        return userDao.save(existingUser);
    }

    public void updateUserPassword(Long userId, String password){
        User existingUser = userDao.findById(userId).orElse(null);

        if (existingUser == null || password == null || password.isEmpty())
            throw new IllegalArgumentException("User not found or password is empty");

        existingUser.setPassword(password);
        userDao.save(existingUser);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
