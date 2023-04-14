package com.sso.payload.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    @Schema(title = "uuid",required = true)
    private String uuid;
    @Schema(example = "admin")
    private String userName;
    private String email;
    private String password;
    @Schema(example = "admin")
    private String fullName;
    @Schema(example = "admin")
    private String firstName;
    @Schema(example = "admin")
    private String lastName;
    @Schema(example = "0981345678")
    private String phone;
    private String address;
    private String avatar;
}
