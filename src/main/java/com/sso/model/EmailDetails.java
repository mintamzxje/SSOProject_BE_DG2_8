package com.sso.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email_detail")
public class EmailDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Email
    @NotEmpty
    @Schema(example = "pdphuoc-batch12bd@sdc.edu.vn")
    @Column
    private String recipient;
    @NotEmpty
    @Schema(example = "Email Scheduler")
    @Column
    private String msgBody;
    @NotEmpty
    @Schema(example = "Email Scheduler")
    @Column
    private String subject;
    @Column
    private String attachment;
    @Column
    private String[] cc;
    @NotNull
    @Column
    private LocalDateTime dateTime;
    @NotNull
    @Schema(example = "Asia/Bangkok")
    @Column
    private ZoneId timeZone;

}
