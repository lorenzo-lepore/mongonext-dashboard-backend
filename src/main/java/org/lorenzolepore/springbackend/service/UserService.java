package org.lorenzolepore.springbackend.service;

import java.util.List;
import java.util.Optional;

import org.lorenzolepore.springbackend.model.User;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    Optional <User> getUserByEmail(String email);
    Optional<User> getUserById(String id);
    User updateUser(User existingUser, String email, String name);
    boolean deleteUser(String id);
}
