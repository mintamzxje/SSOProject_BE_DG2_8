package com.sso.service;

import com.sso.model.User;
import com.sso.payload.dto.ComponentDTO;
import com.sso.payload.request.AddUserToComponentRequest;
import com.sso.payload.request.ComponentDTORequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ComponentService {
    List<ComponentDTO> getAllComponent();
    ComponentDTO getComponentByUUID(String uuid);
    List<ComponentDTO> getComponentByUserUUID(String uuid);
    Set<User> getAllUserInComponent(String uuid);
    ComponentDTO createComponent(ComponentDTORequest componentDTORequest, MultipartFile file);
    ComponentDTO addUserToComponent(String uuid, AddUserToComponentRequest user);
    ComponentDTO updateComponent(ComponentDTORequest componentDTORequest, String uuid, MultipartFile file);
    Boolean deleteComponent(String uuid);
    Boolean existsByUUID(String uuid);
}
