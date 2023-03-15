package com.sso.service;

import com.sso.dto.UserDTO;
import com.sso.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User addUser(User user);
    User updateUser(String uuid, User user);
    UserDTO getOneUser(String uuid);
    Page<User> getPage(Pageable pageable);
    boolean delete(String uuid);
    String forgotPassword(String email);
    String resetPassword(String token, String password);

    boolean existsByUuid(String uuid);
    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
