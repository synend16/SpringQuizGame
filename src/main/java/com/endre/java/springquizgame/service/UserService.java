package com.endre.java.springquizgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.endre.java.springquizgame.entity.User;

import javax.persistence.EntityManager;
import java.util.Collections;


@Service
@Transactional
public class UserService {


    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createUser(String username, String password){

        String hashedPassword = passwordEncoder.encode(password);

        if (em.find(User.class, username) != null){
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setRoles(Collections.singleton("USER"));
        user.setEnabled(true);

        return true;
    }

}
