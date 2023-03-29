package com.sso.security.userprincal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sso.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrinciple implements UserDetails {
    private String idUser;
    private String fullName;
    private String lastName;
    private String firstName;
    private String userName;
    @JsonIgnore
    private String password;
    private String email;
    private String phone;

    public UserPrinciple() {
    }

    public UserPrinciple(String idUser, String fullName, String lastName, String firstName, String userName,
                         String password, String email, String phone) {
        this.idUser = idUser;
        this.fullName = fullName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
    public static UserPrinciple build(User user) {
        return new UserPrinciple(
                user.getUuid(),
                user.getFullName(),
                user.getLastName(),
                user.getFirstName(),
                user.getUserName(),
                user.getPassWord(),
                user.getEmail(),
                user.getPhone()
        );
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
