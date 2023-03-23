package com.sso.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
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

    @ManyToMany(mappedBy = "users")
    @JsonBackReference
    private Set<Component> components = new HashSet<>();

    public User(){

    }

    public User(String uuid, String userName, String passWord, String fullName, String firstName, String lastName,
                String phone, String email, String address, String avatar, String token,
                LocalDateTime tokenCreationDate) {
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
        this.token = token;
        this.tokenCreationDate = tokenCreationDate;
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


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTokenCreationDate() {
        return tokenCreationDate;
    }

    public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
        this.tokenCreationDate = tokenCreationDate;
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

    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }
    public void setComponent(Component component){
        this.components.add(component);
    }

}
