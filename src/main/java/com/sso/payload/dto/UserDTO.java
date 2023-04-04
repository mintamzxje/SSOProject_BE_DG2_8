package com.sso.payload.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String userName;
    @Schema(title = "email",required = true, pattern = "/^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$/")
    private String email;
    @Schema(example = "124124")
    private String password;
    private String fullName;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String avatar;
}
