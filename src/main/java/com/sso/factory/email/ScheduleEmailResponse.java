package com.sso.factory.email;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleEmailResponse {
    private boolean success;
    private HttpStatus code;
    private String errorMessage;
    private Object data;

}
