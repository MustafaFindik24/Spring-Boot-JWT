package com.mustafafindik.security.TokenApp.Service;

import com.mustafafindik.security.TokenApp.Entity.User;
import com.mustafafindik.security.TokenApp.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getOneUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save((user));
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
