package com.sso.service.impl;

import com.sso.dto.UserDTO;
import com.sso.model.User;
import com.sso.repository.UserRepository;
import com.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String uuid, User user) {
        if(userRepository.existsById(uuid)) {
            User user1 = userRepository.getReferenceById(uuid);
            user1.setUserName(user.getUserName());
            user1.setFirstName(user.getFirstName());
            user1.setAddress(user.getAddress());
            user1.setFullName(user.getFullName());
            user1.setPhone(user.getPhone());
            user1.setAvatar(user.getAvatar());
            user1.setLastName(user.getLastName());
            user1.setAddress(user.getAddress());
            user1.setPassWord(user.getPassWord());
            return userRepository.saveAndFlush(user1);
        }
        return null;
    }

    @Override
    public UserDTO getOneUser(String uuid) {
        User user = userRepository.getReferenceById(uuid);
        UserDTO userDTO = new UserDTO(
                user.getUuid(),
                user.getUserName(),
                user.getEmail(),
                user.getPassWord(),
                user.getFullName(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getAddress(),
                user.getAvatar()
        );
        return userDTO;
    }

    @Override
    public Page<User> getPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public boolean delete(String uuid) {
        if(userRepository.existsById(uuid)) {
            userRepository.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByUuid(String uuid) {
        return userRepository.existsById(uuid);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
