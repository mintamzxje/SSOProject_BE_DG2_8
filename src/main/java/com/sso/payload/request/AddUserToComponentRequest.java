package com.sso.payload.request;

import com.sso.model.User;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AddUserToComponentRequest{
    @NotBlank
    private String user_uuid;

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }
}
