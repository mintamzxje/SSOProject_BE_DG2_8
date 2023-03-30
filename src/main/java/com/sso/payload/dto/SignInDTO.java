package com.sso.payload.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInDTO {
    private String userName;
    private String password;
}
