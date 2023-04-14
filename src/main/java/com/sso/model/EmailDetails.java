package com.sso.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "pdphuoc-batch12bd@sdc.edu.vn")
    private String recipient;
    @NotEmpty
    @Schema(example = "Email Scheduler")
    private String msgBody;
    @NotEmpty
    @Schema(example = "Email Scheduler")
    private String subject;
    private String attachment;
    private String[] cc;
    @NotNull
    private LocalDateTime dateTime;
    @NotNull
    @Schema(example = "Asia/Bangkok")
    private ZoneId timeZone;

}
