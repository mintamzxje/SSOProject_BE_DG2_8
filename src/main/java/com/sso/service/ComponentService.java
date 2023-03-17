package com.sso.service;

import com.sso.model.User;
import com.sso.payload.dto.ComponentDTO;
import com.sso.payload.request.AddUserToComponentRequest;
import com.sso.model.Component;

import java.util.List;
import java.util.Set;

public interface ComponentService {
    List<ComponentDTO> getAllComponent();
    ComponentDTO createComponent(ComponentDTO componentDTO);
    ComponentDTO addUserToComponent(String uuid, AddUserToComponentRequest user);
    ComponentDTO updateComponent(ComponentDTO componentDTO, String uuid);
    Boolean deleteComponent(String uuid);
    ComponentDTO getComponentById(String uuid);
    List<ComponentDTO> getComponentByUserUuid(String uuid);
    Set<User> getAllUserInComponent(String uuid);
}
