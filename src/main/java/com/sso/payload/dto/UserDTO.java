package com.sso.payload.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String uuid;
    @Schema(example = "admin")
    private String userName;
    @Schema(example = "admin@gmail.com")
    private String email;
    @Schema(example = "123")
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
