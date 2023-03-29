package com.sso.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private long id;
    private String token;
    private String type = "Bearer";
    private String idUser;
    private String name;
    public JwtResponse(String token, String idUser, String fullName) {
        this.token = token;
        this.idUser = idUser;
        this.name = fullName;
    }
}
