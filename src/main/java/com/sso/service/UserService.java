package com.sso.service;

import com.sso.payload.dto.UserDTO;
import com.sso.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserDTO addUser(UserDTO userDTO, MultipartFile multipartFile);
    UserDTO updateUser(String uuid, UserDTO userDTO, MultipartFile multipartFile);
    List<UserDTO> getAll();
    UserDTO getOneUser(String uuid);
    Page<User> getPage(Pageable pageable);
    boolean delete(String uuid);
    String forgotPassword(String email);
    String resetPassword(String token, String password);
    UserDTO singUp(UserDTO userDTO);

    boolean existsByUuid(String uuid);
    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
