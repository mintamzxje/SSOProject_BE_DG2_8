package com.sso.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailDetails {
    @Email
    @NotEmpty
    private String recipient;
    @NotEmpty
    private String msgBody;
    @NotEmpty
    private String subject;
    private String attachment;
    private String[] cc;
    @NotNull
    private LocalDateTime dateTime;
    @NotNull
    private ZoneId timeZone;

}
