package com.sso.SSO_BE_DG2_8.service.impl;

import com.sso.SSO_BE_DG2_8.model.Component;
import com.sso.SSO_BE_DG2_8.repository.ComponentRepository;
import com.sso.SSO_BE_DG2_8.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComponentServiceImpl implements ComponentService {
    @Autowired
    private ComponentRepository componentRepository;

    @Override
    public List<Component> getAllComponent() {
        return componentRepository.findAll();
    }

    @Override
    public Component createComponent(Component component) {
        return componentRepository.save(component);
    }

    @Override
    public Component updateComponent(Component component, String uuid) {
        Optional<Component> check = componentRepository.findById(uuid);
        if (check != null){
            componentRepository.saveAndFlush(component);
            return component;
        }
        return null;
    }

    @Override
    public Boolean deleteComponent(String uuid) {
        Optional<Component> check = componentRepository.findById(uuid);
        if (check != null){
            componentRepository.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Component> getComponentById(String uuid) {
        return componentRepository.findById(uuid);
    }
}
