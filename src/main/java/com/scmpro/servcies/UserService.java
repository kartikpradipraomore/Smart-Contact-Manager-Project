package com.scmpro.servcies;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scmpro.entities.User;

@Service
public interface UserService  {

    User saveUser(User user);
    User getUser(String id);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String userId);
    User getUserByEmail(String email);
    boolean isUserExistByEmail(String email);
    

}
