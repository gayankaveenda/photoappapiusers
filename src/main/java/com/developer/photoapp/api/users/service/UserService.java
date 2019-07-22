package com.developer.photoapp.api.users.service;

import com.developer.photoapp.api.users.model.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    UserDTO getUserById(String userId);

    UserDTO create(UserDTO userDTO);

    UserDTO update(String userId, UserDTO userDTO);
}
