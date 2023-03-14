package com.sso.service.impl;

import com.sso.model.Component;
import com.sso.repository.ComponentRepository;
import com.sso.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ComponentServiceImpl implements ComponentService {
    @Autowired
    private ComponentRepository componentRepository;

    @Override
    @Transactional
    public List<Component> getAllComponent() {
        return componentRepository.findAll();
    }

    @Override
    @Transactional
    public Component createComponent(Component component) {
        return componentRepository.save(component);
    }

    @Override
    @Transactional
    public Component addUserToComponent(Component component) {
        return componentRepository.saveAndFlush(component);
    }

    @Override
    @Transactional
    public Component updateComponent(Component component, String uuid) {
        Optional<Component> check = componentRepository.findById(uuid);
        if (check != null){
            componentRepository.save(component);
            return component;
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean deleteComponent(String uuid) {
        Optional<Component> check = componentRepository.findById(uuid);
        if (check != null){
            componentRepository.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Component getComponentById(String uuid) {
        return componentRepository.findById(uuid).orElse(null);
    }
}
