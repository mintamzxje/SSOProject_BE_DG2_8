package com.sso.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sso.dto.UserDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

    public User() {
    }
    public User(String uuid, String userName, String passWord, String fullName, String firstName, String lastName,
                String phone, String email, String address, String avatar) {
        this.uuid = uuid;
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

    public User(String userName, String name, String email, UserDTO userDTO, String passWord) {
        this.userName = userName;
        this.fullName = name;
        this.email = email;
        this.passWord = passWord;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}