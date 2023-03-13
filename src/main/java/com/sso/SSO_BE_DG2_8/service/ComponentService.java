package com.sso.SSO_BE_DG2_8.service;

import com.sso.SSO_BE_DG2_8.model.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComponentService {
    List<Component> getAllComponent();
    Component createComponent(Component componentDTO);
    Component updateComponent(Component componentDTO, String uuid);
    Boolean deleteComponent(String uuid);
    Optional<Component> getComponentById(String uuid);
}
