package com.sso.service;

import com.sso.model.Component;

import java.util.List;
import java.util.Optional;

public interface ComponentService {
    List<Component> getAllComponent();
    Component createComponent(Component component);
    Component addUserToComponent(Component component);
    Component updateComponent(Component component, String uuid);
    Boolean deleteComponent(String uuid);
    Component getComponentById(String uuid);
}
