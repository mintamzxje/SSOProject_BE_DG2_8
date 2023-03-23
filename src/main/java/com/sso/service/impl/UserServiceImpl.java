package com.sso.service.impl;

import com.sso.exception.DuplicateRecordException;
import com.sso.exception.NotFoundException;
import com.sso.factory.encodepassword.MyPasswordEncoder;
import com.sso.factory.file.FilesStorageService;
import com.sso.mapper.UserMapper;
import com.sso.payload.dto.UserDTO;
import com.sso.model.User;
import com.sso.repository.UserRepository;
import com.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl  implements UserService {
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;
    @Autowired
    private FilesStorageService filesStorageService;
    @Override
    public UserDTO addUser(UserDTO userDTO, MultipartFile file) {
        User user = UserMapper.MAPPER.mapToUser(userDTO);
        if(file.getOriginalFilename() != null) {
            filesStorageService.delete(file.getOriginalFilename(),"/users/");
        }
        filesStorageService.saveAs(file, "/users/" + file.getOriginalFilename());
        user.setAvatar(file.getOriginalFilename());
        user.setPassWord(myPasswordEncoder.encode(userDTO.getPassword()));
        return UserMapper.MAPPER.mapToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(String uuid, UserDTO userDTO, MultipartFile file) {
        if(userRepository.existsById(uuid)) {
            User user = UserMapper.MAPPER.mapToUser(userDTO);
            User userDB = userRepository.findById(uuid).orElseThrow(() ->
                    new RuntimeException("Find not user id: "+uuid)
            );

            if(file.getOriginalFilename().equals(userDB.getAvatar())) {
                filesStorageService.delete(userDB.getAvatar(),"/users/");
            } else {
                filesStorageService.delete(file.getOriginalFilename(), "/users/");
            }
            filesStorageService.saveAs(file, "/users/"+file.getOriginalFilename());
            user.setUuid(userDB.getUuid());
            user.setAvatar(file.getOriginalFilename());
            user.setPassWord(myPasswordEncoder.encode(userDTO.getPassword()));
            return UserMapper.MAPPER.mapToUserDTO(userRepository.saveAndFlush(user));
        }
        throw new NotFoundException("Find not id: "+uuid);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new DuplicateRecordException("users is empty");
        }
        return UserMapper.MAPPER.mapToUserDTOList(users);
    }

    @Override
    public UserDTO getOneUser(String uuid) {
        User user = userRepository.findById(uuid).orElseThrow(() ->
                new RuntimeException("Find not user id: "+uuid)
        );
        return UserMapper.MAPPER.mapToUserDTO(user);
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
