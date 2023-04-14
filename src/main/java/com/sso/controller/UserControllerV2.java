package com.sso.controller;

import com.sso.exception.NotFoundException;
import com.sso.model.EmailDetails;
import com.sso.model.User;
import com.sso.payload.dto.SignInDTO;
import com.sso.payload.dto.UserDTO;
import com.sso.payload.response.JwtResponse;
import com.sso.payload.response.ResponseDTO;
import com.sso.security.jwt.JwtProvider;
import com.sso.security.userprincal.UserPrinciple;
import com.sso.service.EmailSendService;
import com.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v2")
public class UserControllerV2 {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSendService emailSendService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;
    @GetMapping("/")
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true,HttpStatus.OK,"null",userService.getAll())
        );
    }
    @GetMapping("/id")
    public ResponseEntity<?> getOneUserDTO(@RequestParam("id") String uuid) {
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
    @PostMapping(value ="/add")
    public ResponseEntity<?> add(@RequestPart(value = "file") MultipartFile file,
                                 UserDTO userDTO) {
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
    public ResponseEntity<?> update(@RequestParam("id") String uuid, @RequestBody UserDTO userDTO,
                                    @RequestPart("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK,"null",userService.updateUser(uuid,userDTO,file))
        );
    }
    @DeleteMapping("/")
    public ResponseEntity<?> delete(@RequestParam String uuid) {
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
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token, @RequestParam("password") String password) {
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
    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO result = userService.singUp(userDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getUuid()).toUri();
        return ResponseEntity.created(location).body(
                new ResponseDTO(true,HttpStatus.OK,"User registered successfully",userDTO)
        );
    }
    @PostMapping("/signin")
    public ResponseEntity<?> login (@Valid @RequestBody SignInDTO signIn) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getUserName(),signIn.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new ResponseDTO(true,HttpStatus.OK,"null",new JwtResponse(token,
                userPrinciple.getIdUser(),userPrinciple.getFullName())));
    }
}
