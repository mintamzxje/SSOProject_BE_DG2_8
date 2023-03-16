package com.sso.service;

import com.sso.payload.dto.ComponentDTO;
import com.sso.payload.request.AddUserToComponentRequest;
import com.sso.model.Component;

import java.util.List;

public interface ComponentService {
    List<ComponentDTO> getAllComponent();
    ComponentDTO createComponent(ComponentDTO componentDTO);
    Component addUserToComponent(String uuid, AddUserToComponentRequest user);
    ComponentDTO updateComponent(ComponentDTO componentDTO, String uuid);
    Boolean deleteComponent(String uuid);
    ComponentDTO getComponentById(String uuid);
    List<ComponentDTO> getComponentByUserUuid(String uuid);
}
