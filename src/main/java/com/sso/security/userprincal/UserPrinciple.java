package com.sso.security.userprincal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sso.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class UserPrinciple implements OAuth2User, UserDetails {
    private String idUser;
    private String fullName;
    private String lastName;
    private String firstName;
    private String userName;
    @JsonIgnore
    private String password;
    private String email;
    private String phone;
    private Map<String, Object> attributes;

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
    public static UserPrinciple create(User user, Map<String, Object> attributes) {
        UserPrinciple userPrincipal = UserPrinciple.build(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
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
    public Map<String, Object> getAttributes() {
        return attributes;
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

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    @Override
    public String getName() {
        return String.valueOf(idUser);
    }
}
