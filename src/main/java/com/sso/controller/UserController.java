package com.sso.controller;

import com.sso.exception.DuplicateRecordException;
import com.sso.exception.NotFoundException;
import com.sso.payload.dto.UserDTO;
import com.sso.payload.response.ResponseDTO;

import com.sso.model.EmailDetails;
import com.sso.model.User;
import com.sso.service.EmailSendService;
import com.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/auth/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSendService emailSendService;
    @GetMapping("/")
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true,HttpStatus.OK,"null",userService.getAll())
        );
    }

    @GetMapping("/id")
    public ResponseEntity<?> getOneUserDTO(@RequestParam String uuid) {
        if (!userService.existsByUuid(uuid)) {
            throw new NotFoundException("ID not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true,HttpStatus.OK,"null",userService.getOneUser(uuid))
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseDTO(false,HttpStatus.BAD_REQUEST,"user list empty",null)
                );
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("users",users);
                response.put("currentPage", page.getNumber());
                response.put("totalItem",page.getTotalElements());
                response.put("totalPage",page.getTotalPages());
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO(true,HttpStatus.OK,"null",response)
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @ModelAttribute UserDTO userDTO,
                                 @RequestPart(name = "file") MultipartFile file) {
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            throw new NotFoundException("Only JPG and PNG images are supported");
        }
        if (userService.existsByUserName(userDTO.getUserName())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO(false,HttpStatus.NOT_FOUND,
                            "the username existed! please try again!",null)
            );
        }
        if (userService.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO(false,HttpStatus.NOT_FOUND,
                            "the email existed! please try again!",null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK,"null",userService.addUser(userDTO,file))
        );
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam String uuid, @ModelAttribute UserDTO userDTO,
                                    @RequestPart MultipartFile file) {
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            throw new NotFoundException("Only JPG and PNG images are supported");
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK,"null",userService.updateUser(uuid,userDTO,
                        file))
        );
    }
    @DeleteMapping("/")
    public ResponseEntity<?> delete( @RequestParam String uuid) {
        if (!userService.existsByUuid(uuid)) {
            throw new NotFoundException("ID not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true,HttpStatus.OK, "null",userService.delete(uuid))
        );
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> resetPassword(HttpServletRequest request, @RequestParam("email") String email) {
        String response = userService.forgotPassword(email);
        if(!response.startsWith("Invalid")) {
            String url= "http://localhost:8080/swagger-ui/#/user-controller/resetPasswordUsingPUT";
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setSubject("Forgot Password");
            emailDetails.setMsgBody(
                    "url change password: "+url+" token: "+response
            );
            emailDetails.setRecipient(email);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO(true,HttpStatus.OK,"null",
                            emailSendService.sendSimpleMail(emailDetails))
            );
        } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseDTO(false,HttpStatus.NOT_FOUND,"find not email: "+email,null)
        );
        }
    }
    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String password) {
        String response = userService.resetPassword(token,password);
        if(response.startsWith("Invalid")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO(false,HttpStatus.NOT_FOUND,"find not token: "+token,null)
            );
        } else if (response.contains("Token expired.")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO(false,HttpStatus.NOT_FOUND,"Token expired.",null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO(true,HttpStatus.OK,"null",response)
            );
        }
    }
}
