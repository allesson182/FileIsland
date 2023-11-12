package com.fileisland.datakeeper.Services;

import com.fileisland.datakeeper.Dao.Entity.User;
import com.fileisland.datakeeper.Dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);


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
        User user = userDao.findById(userId).orElse(null);

        if (user == null || password == null || password.isEmpty())
            throw new IllegalArgumentException("User not found or password is empty");
        String encryptedPass = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encryptedPass);
        userDao.save(user);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public void createUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setEmail(email);
        userDao.save(user);
        LOGGER.info("User: ".concat(username).concat(" created successfully"));
    }
}
