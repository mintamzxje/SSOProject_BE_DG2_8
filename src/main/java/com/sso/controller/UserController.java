package com.sso.controller;

import com.sso.dto.UserDTO;
import com.sso.dto.response.ResponseDTO;
import com.sso.model.User;
import com.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<?> getOneUserDTO(@RequestParam String uuid) {
        if (!userService.existsByUuid(uuid)) {
            return new ResponseEntity<>(new ResponseDTO("error", "find not uuid: " + uuid),
                    HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO("","get success",userService.getOneUser(uuid))
        );
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPage(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        try {
            List<User> users = new ArrayList<>();
            Pageable pageable = PageRequest.of(offset, limit);
            Page<User> page = userService.getPage(pageable);
            users = page.getContent();
            if(users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("error","user list empty")
                );
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("users",users);
                response.put("currentPage", page.getNumber());
                response.put("totalItem",page.getTotalElements());
                response.put("totalPage",page.getTotalPages());
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO(response)
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody UserDTO userDTO) {
        if (userService.existsByUserName(userDTO.getUserName())) {
            return new ResponseEntity<>(new ResponseDTO("error", "the username existed! please try again!"),
                    HttpStatus.OK);
        }
        if (userService.existsByEmail(userDTO.getEmail())) {
            return new ResponseEntity<>(new ResponseDTO("error", "the email existed! please try again!"),
                    HttpStatus.OK);
        }
        User user = new User(userDTO.getUserName(),passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getFullName(),userDTO.getFirstName(),userDTO.getLastName(),userDTO.getPhone(),userDTO.getEmail(),
                userDTO.getAddress(),userDTO.getAvatar());
        userService.addUser(user);
        return new ResponseEntity<>(new ResponseDTO(userDTO), HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam String uuid, @RequestBody UserDTO userDTO) {
        if (!userService.existsByUuid(uuid)) {
            return new ResponseEntity<>(new ResponseDTO("error", "find not uuid: " + uuid),
                    HttpStatus.OK);
        }
        if (userService.existsByUserName(userDTO.getUserName())) {
            return new ResponseEntity<>(new ResponseDTO("error", "can't change username"),
                    HttpStatus.OK);
        }
        User user = new User(userDTO.getUserName(),passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getFullName(),userDTO.getFirstName(),userDTO.getLastName(),userDTO.getPhone(),userDTO.getEmail(),
                userDTO.getAddress(),userDTO.getAvatar());
        userService.updateUser(uuid,user);
        return new ResponseEntity<>(new ResponseDTO(userDTO), HttpStatus.OK);
    }
    @DeleteMapping("/")
    public ResponseEntity<?> delete( @RequestParam String uuid) {
        if (!userService.existsByUuid(uuid)) {
            return new ResponseEntity<>(new ResponseDTO("error", "find not uuid: " + uuid),
                    HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO("","delete success",userService.delete(uuid))
        );
    }
}
