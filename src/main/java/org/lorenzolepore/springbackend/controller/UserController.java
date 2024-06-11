package org.lorenzolepore.springbackend.controller;

import org.bson.types.ObjectId;
import org.lorenzolepore.springbackend.model.User;
import org.lorenzolepore.springbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test() {
        return "User Controller is working";
    }

    @GetMapping("/savetest")
    public String saveTest() {
        User user = new User("new ObjectId()", "mail@mail.com", "test", "password");
        userService.saveUser(user);
        return "User saved.";
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/id/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/add")
    public String add(@RequestBody User user) {
        userService.saveUser(user);
        return "New user added.";
    }
}
