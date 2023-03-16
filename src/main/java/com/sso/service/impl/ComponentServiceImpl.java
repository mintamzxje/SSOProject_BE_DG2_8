package com.sso.service.impl;

import com.sso.payload.dto.ComponentDTO;
import com.sso.payload.request.AddUserToComponentRequest;
import com.sso.mapper.ComponentMapper;
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
    public List<ComponentDTO> getAllComponent() {
        List<Component> component = componentRepository.findAll();
        return ComponentMapper.MAPPER.mapListToComponentDTO(component);
    }

    @Override
    @Transactional
    public ComponentDTO createComponent(ComponentDTO componentDTO) {
        Component component = ComponentMapper.MAPPER.mapToComponent(componentDTO);
        componentRepository.save(component);
        return ComponentMapper.MAPPER.mapToComponentDTO(component);
    }

    @Override
    @Transactional
    public Component addUserToComponent(String uuid, AddUserToComponentRequest user) {
        ComponentDTO componentDTO = getComponentById(uuid);
        Component component = ComponentMapper.MAPPER.mapToComponent(componentDTO);
        component.setUsers(user.getUsers());
        return componentRepository.saveAndFlush(component);
    }

    @Override
    @Transactional
    public ComponentDTO updateComponent(ComponentDTO componentDTO, String uuid) {
        Component component = ComponentMapper.MAPPER.mapToComponent(componentDTO);
        Component existing = componentRepository.findById(uuid).get();
        if (existing != null){
            component.setUuid(existing.getUuid());
            return ComponentMapper.MAPPER.mapToComponentDTO(componentRepository.saveAndFlush(component));
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
    public ComponentDTO getComponentById(String uuid) {
        Component component = componentRepository.findById(uuid).get();
        return ComponentMapper.MAPPER.mapToComponentDTO(component);
    }

    @Override
    @Transactional
    public List<ComponentDTO> getComponentByUserUuid(String uuid) {
        List<Component> component = componentRepository.GetComponentByUserUUID(uuid);
        return ComponentMapper.MAPPER.mapListToComponentDTO(component);
    }
}
