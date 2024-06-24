package org.lorenzolepore.springbackend.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.lorenzolepore.springbackend.model.User;
import org.lorenzolepore.springbackend.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /* TESTING related */

    @PostMapping("/saveTest")
    public ResponseEntity<User> saveTest() {
        return new ResponseEntity<>(userService.saveUser(new User(new ObjectId(), "example@mail.com", "Example Example", "example-password")), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("User Controller is working", HttpStatus.OK);
    }

    /* CRUD methods */

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user) {;
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(
            @RequestParam String id,
            @RequestParam (required = false) Optional<String> email,
            @RequestParam (required = false) Optional<String> name
    ) {
        Optional<User> existingUser = userService.getUserById(id);

        String emailValue = email.orElse(null);
        String nameValue = name.orElse(null);

        if (emailValue == null && nameValue == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (emailValue != null && !emailValue.contains("@") && (!emailValue.contains(".com") || !emailValue.contains(".it"))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (existingUser.isPresent()) {
            User savedUser = userService.updateUser(existingUser.get(), emailValue, nameValue);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteInvoice(@RequestParam String id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
