package com.sso.dto.request;

import com.sso.model.User;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AddUserToComponentRequest{
    @NotBlank
    private Set<User> users;

    public Set<User> getUses() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
