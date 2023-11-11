package com.fileisland.datakeeper.Security;

import com.fileisland.datakeeper.Dao.Entity.User;
import com.fileisland.datakeeper.Dao.UserDao;
import com.fileisland.datakeeper.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user != null)
            return user;
        else
             return null;
    }
}
