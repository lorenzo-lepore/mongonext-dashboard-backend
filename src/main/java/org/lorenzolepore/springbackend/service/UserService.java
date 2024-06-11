package org.lorenzolepore.springbackend.service;

import org.lorenzolepore.springbackend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public void saveUser(User user);
    public List<User> getAllUsers();
    public User getUserByEmail(String email);
    public Optional<User> getUserById(String id);
}
