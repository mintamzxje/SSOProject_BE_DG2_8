package com.sso.service.impl;

import com.sso.exception.DuplicateRecordException;
import com.sso.exception.NotFoundException;
import com.sso.factory.file.FilesStorageService;
import com.sso.model.User;
import com.sso.payload.dto.ComponentDTO;
import com.sso.payload.request.AddUserToComponentRequest;
import com.sso.mapper.ComponentMapper;
import com.sso.model.Component;
import com.sso.repository.ComponentRepository;
import com.sso.repository.UserRepository;
import com.sso.service.ComponentService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ComponentServiceImpl implements ComponentService {
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilesStorageService filesStorageService;

    @Override
    @Transactional
    public List<ComponentDTO> getAllComponent() {
        List<Component> component = componentRepository.findAll();
        return ComponentMapper.MAPPER.mapListToComponentDTO(component);
    }

    @Override
    @Transactional
    public ComponentDTO createComponent(ComponentDTO componentDTO, MultipartFile file) {
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            throw new DuplicateRecordException("Only JPG and PNG images are supported");
        }
        Component component = ComponentMapper.MAPPER.mapToComponent(componentDTO);
        String originalFilename = file.getOriginalFilename();
        String newFilename = component.getName() + filesStorageService.getFileExtension(originalFilename);
        filesStorageService.saveAs(file, "/component/" + newFilename);
        component.setIcon(newFilename);
        return ComponentMapper.MAPPER.mapToComponentDTO(componentRepository.save(component));
    }

    @Override
    @Transactional
    public ComponentDTO addUserToComponent(String uuid, @NotNull AddUserToComponentRequest user) {
        Component component = componentRepository.findById(uuid).orElse(null);
        if (component != null){
            component.getUsers().addAll(user
                    .getUsers()
                    .stream()
                    .map(us -> {
                        User users = us;
                        if(users.getUuid().length() > 0){
                            users = userRepository.findById(users.getUuid()).get();
                        }
                        users.setComponent(component);
                        return users;
                    }).collect(Collectors.toList()));
        }
        return ComponentMapper.MAPPER.mapToComponentDTO(componentRepository.save(component));
    }

    @Override
    @Transactional
    public ComponentDTO updateComponent(ComponentDTO componentDTO, String uuid, MultipartFile file) {
        Component component = ComponentMapper.MAPPER.mapToComponent(componentDTO);
        Component existing = componentRepository.findById(uuid).orElse(null);
        if (existing != null){
            component.setUuid(existing.getUuid());
            if(component.getIcon() != existing.getIcon() && component.getIcon() != null)
            {
                filesStorageService.delete(existing.getIcon(),"/component/");
                String originalFilename = file.getOriginalFilename();
                String newFilename = component.getName() + filesStorageService.getFileExtension(originalFilename);
                filesStorageService.saveAs(file, "/component/" + newFilename);
                component.setIcon(newFilename);
            }
            return ComponentMapper.MAPPER.mapToComponentDTO(componentRepository.save(component));
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean deleteComponent(String uuid) {
        Component check = componentRepository.findById(uuid).orElse(null);
        if (check != null){
            filesStorageService.delete(check.getIcon(),"/component/");
            componentRepository.deleteById(check.getUuid());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public ComponentDTO getComponentById(String uuid) {
        Component component = componentRepository.findById(uuid).orElse(null);
        return ComponentMapper.MAPPER.mapToComponentDTO(component);
    }

    @Override
    @Transactional
    public List<ComponentDTO> getComponentByUserUuid(String uuid) {
        List<Component> component = componentRepository.findComponentsByUsersUuid(uuid);
        return ComponentMapper.MAPPER.mapListToComponentDTO(component);
    }

    @Override
    public Set<User> getAllUserInComponent(String uuid) {
        Component component = componentRepository.findById(uuid).orElse(null);
        return component.getUsers();
    }

    @Override
    public Boolean existsById(String uuid) {
        return componentRepository.existsById(uuid);
    }
}
