package com.developer.photoapp.api.users.service;

import com.developer.photoapp.api.users.domain.User;
import com.developer.photoapp.api.users.mapper.UserMapper;
import com.developer.photoapp.api.users.model.UserDTO;
import com.developer.photoapp.api.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDTO getUserById(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return userMapper.userToUserDto(user.get());
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());
        User user = userRepository.saveAndFlush(userMapper.userDtoToUser(userDTO));
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDTO update(String userId, UserDTO userDTO) {
        Optional<User> userFromDB = userRepository.findByUserId(userId);
        userFromDB.ifPresent(user -> {
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());

            userRepository.saveAndFlush(user);
        });
        return userMapper.userToUserDto(userFromDB.get());
    }
}
