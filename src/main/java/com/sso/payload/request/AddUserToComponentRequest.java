package com.sso.payload.request;

import com.sso.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserToComponentRequest{
    @NotBlank
    private Set<User> users = new HashSet<>();
}
