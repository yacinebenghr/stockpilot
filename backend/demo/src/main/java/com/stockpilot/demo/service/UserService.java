package com.stockpilot.demo.service;

import com.stockpilot.demo.model.User;
import com.stockpilot.demo.repository.UserRepository;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email){
        Optional<User> userOpt = userRepository.findUserByEmail(email);
        return    userOpt.isPresent()  ?
                  userOpt.get()
                  : null ;
    }
}
