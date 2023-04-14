package com.sso.mapper;

import com.sso.model.User;
import com.sso.payload.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    List<UserDTO> mapToUserDTOList(List<User> users);
    User mapToUser(UserDTO userDTO);
    UserDTO mapToUserDTO(User user);
}
