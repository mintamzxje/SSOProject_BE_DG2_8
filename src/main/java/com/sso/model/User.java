package com.sso.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "userName"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;
    @Column(name = "username")
    @NotBlank
    private String userName;
    @NotBlank
    @JsonIgnore
    @Column(name = "password")
    private String passWord;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phone;
    @Column(name = "email")
    @Email
    @NotBlank
    private String email;
    @Column(name = "address")
    private String address;
    @Lob
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "token")
    private String token;
    @Column(name = "token_creation_date")
    private LocalDateTime tokenCreationDate;

    public User(@NotBlank String userName,
                @NotBlank String passWord,
                @NotBlank String fullName,
                @NotBlank String firstName,
                @NotBlank String lastName,
                @NotBlank String phone,
                @Email @NotBlank String email,
                @NotBlank String address,
                @NotBlank String avatar) {
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.avatar = avatar;
    }
}
