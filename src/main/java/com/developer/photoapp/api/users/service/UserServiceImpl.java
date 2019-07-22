package com.developer.photoapp.api.users.service;

import com.developer.photoapp.api.users.domain.User;
import com.developer.photoapp.api.users.mapper.UserMapper;
import com.developer.photoapp.api.users.model.UserDTO;
import com.developer.photoapp.api.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDTO getUserById(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return userMapper.userToUserDto(user.get());
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());
        User user = userMapper.userDtoToUser(userDTO);
        user.setEncryptedPassword(bCryptPasswordEncoder().encode(userDTO.getPassword()));
        return userMapper.userToUserDto(userRepository.saveAndFlush(user));
    }

    @Override
    public UserDTO update(String userId, UserDTO userDTO) {
        Optional<User> userFromDB = userRepository.findByUserId(userId);
        userFromDB.ifPresent(user -> {
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setEncryptedPassword(bCryptPasswordEncoder().encode(userDTO.getPassword()));

            userRepository.saveAndFlush(user);
        });
        return userMapper.userToUserDto(userFromDB.get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDb = userRepository.findByEmail(username);

        userDb.orElseThrow(() -> new UsernameNotFoundException(username));

        return new org.springframework.security.core.userdetails.User(userDb.get().getEmail(),userDb.get().getEncryptedPassword(),new ArrayList<>());
    }
}
