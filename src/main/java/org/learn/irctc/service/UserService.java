package org.learn.irctc.service;

import org.learn.di.annotation.Component;
import org.learn.irctc.repository.UserRepository;

@Component
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void getUserDetails() {
        System.out.println("User Service : Getting user details");
    }
}
