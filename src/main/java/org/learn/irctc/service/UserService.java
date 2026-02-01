package org.learn.irctc.service;

import di.annotation.Component;
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

    public void createUser() {
        System.out.println("User Service : Creating user");
    }
}
