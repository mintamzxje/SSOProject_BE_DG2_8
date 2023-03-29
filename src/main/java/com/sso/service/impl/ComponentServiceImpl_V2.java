package com.sso.service.impl;

import com.sso.exception.DuplicateRecordException;
import com.sso.exception.NotFoundException;
import com.sso.factory.excel.ExcelHelper;
import com.sso.factory.file.FilesStorageService;
import com.sso.mapper.ComponentMapper;
import com.sso.model.Component;
import com.sso.model.User;
import com.sso.payload.dto.ComponentDTO;
import com.sso.payload.request.AddUserToComponentRequest;
import com.sso.repository.ComponentRepository;
import com.sso.repository.UserRepository;
import com.sso.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComponentServiceImpl_V2 implements ComponentService {
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilesStorageService filesStorageService;

    @Override
    public List<ComponentDTO> getAllComponent() {
        List<Component> components = componentRepository.findAll();
        if(components.isEmpty()){
            throw new NotFoundException("Component Is Empty");
        }
        return ComponentMapper.MAPPER.mapListToComponentDTO(components);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
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
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ComponentDTO addUserToComponent(String uuid, AddUserToComponentRequest user) {
        Component component = componentRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Component Not Found")
        );
        component.getUsers().addAll(user
                .getUsers()
                .stream()
                .map(us -> {
                    User users = us;
                    if(users.getUuid().length() > 0){
                        users = userRepository.findById(users.getUuid())
                                .orElseThrow(() -> new NotFoundException("User Not Found"));
                        users.setComponent(component);
                        return users;
                    }
                    else {
                        throw new DuplicateRecordException("UUID Cannot Be Left Blank");
                    }
                }).collect(Collectors.toList()));
        return ComponentMapper.MAPPER.mapToComponentDTO(componentRepository.save(component));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ComponentDTO updateComponent(ComponentDTO componentDTO, String uuid, MultipartFile file) {
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            throw new DuplicateRecordException("Only JPG and PNG images are supported");
        }
        Component component = ComponentMapper.MAPPER.mapToComponent(componentDTO);
        Component existing = componentRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Component Not Found"));

        component.setUuid(existing.getUuid());
        if(!component.getIcon().equals(existing.getIcon()) && component.getIcon() != null)
        {
            filesStorageService.delete(existing.getIcon(),"/component/");
            String originalFilename = file.getOriginalFilename();
            String newFilename = component.getName() + filesStorageService.getFileExtension(originalFilename);
            filesStorageService.saveAs(file, "/component/" + newFilename);
            component.setIcon(newFilename);
        }
        return ComponentMapper.MAPPER.mapToComponentDTO(componentRepository.save(component));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Boolean deleteComponent(String uuid) {
        Component exists = componentRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Component Not Found"));
        if (exists != null)
        {
            filesStorageService.delete(exists.getIcon(),"/component/");
            componentRepository.deleteById(uuid);
            return true;
        }
        return false;
    }

    @Override
    public ComponentDTO getComponentByUUID(String uuid) {
        Component component = componentRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Not Found UUID: " + uuid)
                );
        return ComponentMapper.MAPPER.mapToComponentDTO(component);
    }

    @Override
    public List<ComponentDTO> getComponentByUserUUID(String uuid) {
        List<Component> components = componentRepository.findComponentsByUsersUuid(uuid);
        if (components.isEmpty()){
            throw new NotFoundException("Components For User Is Empty");
        }
        return ComponentMapper.MAPPER.mapListToComponentDTO(components);
    }

    @Override
    public Set<User> getAllUserInComponent(String uuid) {
        Component component = componentRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Not Found UUID: " + uuid)
        );
        return component.getUsers();
    }

    @Override
    public Boolean existsByUUID(String uuid) {
        return componentRepository.existsById(uuid);
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ComponentDTO importUserFromExcel(MultipartFile file, String uuid) {
        try {
            Set<User> users = ExcelHelper.importExcelUserToComponent(file.getInputStream());
            Component component = componentRepository.findById(uuid)
                    .orElseThrow(() -> new NotFoundException("Component Not Found"));
            component.getUsers().addAll(users);
            return ComponentMapper.MAPPER.mapToComponentDTO(componentRepository.save(component));
        } catch (IOException e) {
            throw new RuntimeException("Fail To Store Excel Data: " + e.getMessage());
        }
    }
}
