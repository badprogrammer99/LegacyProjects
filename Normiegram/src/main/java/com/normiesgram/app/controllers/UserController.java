package com.normiesgram.app.controllers;

import com.normiesgram.app.dtos.UserDTO;
import com.normiesgram.app.entities.User;
import com.normiesgram.app.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "user/{username}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable("username") String username) {
        return Optional
                .ofNullable(userService.getUserByUsername(username))
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "users", method = RequestMethod.POST)
    public UserDTO registerUser(@RequestBody @Valid User user) {
        return userService.registerUser(user);
    }
}
