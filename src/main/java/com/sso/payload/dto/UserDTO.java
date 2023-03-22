package com.sso.payload.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String uuid;
    private String userName;
    private String email;
    private String password;
    private String fullName;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String avatar;

}
