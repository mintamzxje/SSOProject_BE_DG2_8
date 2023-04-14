package com.sso.payload.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseDTO {
    private boolean success;
    private HttpStatus code;
    private String errorMessage;
    private Object data;

}
