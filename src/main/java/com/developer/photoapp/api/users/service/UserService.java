package com.developer.photoapp.api.users.service;

import com.developer.photoapp.api.users.model.UserDTO;


public interface UserService {

    UserDTO getUserById(String userId);

    UserDTO create(UserDTO userDTO);

    UserDTO update(String userId, UserDTO userDTO);
}
