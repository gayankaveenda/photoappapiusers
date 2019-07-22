package com.developer.photoapp.api.users.mapper;

import com.developer.photoapp.api.users.domain.User;
import com.developer.photoapp.api.users.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDto(User user);

    User userDtoToUser(UserDTO userDTO);
}
