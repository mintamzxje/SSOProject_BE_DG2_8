package com.sso.SSO_BE_DG2_8.service;

import com.sso.SSO_BE_DG2_8.model.Component;
import com.sso.SSO_BE_DG2_8.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComponentServiceImpl implements ComponentService{
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
    public Component updateComponent(Component component, UUID uuid) {
        Optional<Component> check = componentRepository.findById(uuid);
        if (check != null){
            componentRepository.saveAndFlush(component);
            return component;
        }
        return null;
    }

    @Override
    public Boolean deleteComponent(UUID uuid) {
        Optional<Component> check = componentRepository.findById(uuid);
        if (check != null){
            componentRepository.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Component> getComponentById(UUID uuid) {
        return componentRepository.findById(uuid);
    }
}
