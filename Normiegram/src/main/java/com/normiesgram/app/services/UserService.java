package com.normiesgram.app.services;

import com.normiesgram.app.dtos.UserDTO;
import com.normiesgram.app.entities.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserByUsername(String username);

    UserDTO registerUser(User user);
}
