package com.sso.service;

import com.sso.payload.dto.UserDTO;
import com.sso.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDTO addUser(UserDTO userDTO);
    UserDTO updateUser(String uuid, UserDTO userDTO);
    List<UserDTO> getAll();
    UserDTO getOneUser(String uuid);
    Page<User> getPage(Pageable pageable);
    boolean delete(String uuid);
    String forgotPassword(String email);
    String resetPassword(String token, String password);

    boolean existsByUuid(String uuid);
    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
