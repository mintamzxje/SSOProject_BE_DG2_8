package com.sso.service;

import com.sso.model.Component;

import java.util.List;
import java.util.Optional;

public interface ComponentService {
    List<Component> getAllComponent();
    Component createComponent(Component componentDTO);
    Component updateComponent(Component componentDTO, String uuid);
    Boolean deleteComponent(String uuid);
    Optional<Component> getComponentById(String uuid);
}
