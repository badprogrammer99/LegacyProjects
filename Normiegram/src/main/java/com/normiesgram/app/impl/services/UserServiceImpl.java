package com.normiesgram.app.impl.services;

import com.normiesgram.app.dtos.UserDTO;
import com.normiesgram.app.repositories.UserRepository;
import com.normiesgram.app.services.UserService;
import com.normiesgram.app.entities.User;

import com.normiesgram.app.utils.ObjectMapperUtils;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapperUtils objectMapperUtils;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
        }

        throw new UsernameNotFoundException(username);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return objectMapperUtils.mapAll(userRepository.findAll(), UserDTO.class);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) return null;

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User newUser = userRepository.saveAndFlush(user);

        if (newUser == null) return null;

        return modelMapper.map(newUser, UserDTO.class);
    }
}
