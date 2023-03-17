package com.sso.service.impl;

import com.sso.payload.dto.UserDTO;
import com.sso.model.User;
import com.sso.repository.UserRepository;
import com.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl  implements UserService {
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
    public String forgotPassword(String email) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isEmpty()) {
            return "Invalid email id.";
        }
        User user = userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreationDate(LocalDateTime.now());
        user = userRepository.save(user);
        return user.getToken();
    }
    @Override
    public String resetPassword(String token, String password) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByToken(token));

        if (userOptional.isEmpty()) {
            return "Invalid token.";
        }
        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();
        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";
        }
        User user = userOptional.get();
        user.setPassWord(passwordEncoder.encode(password));
        user.setToken(null);
        user.setTokenCreationDate(null);
        userRepository.save(user);
        return "Your password successfully updated.";
    }

    private String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }
    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);
        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
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
