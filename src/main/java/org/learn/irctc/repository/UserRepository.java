package org.learn.irctc.repository;

import org.learn.di.annotation.Component;

@Component
public class UserRepository {
    public void getUser(String user) {
        System.out.println("User Repository : Getting User " + user);
    }
}
