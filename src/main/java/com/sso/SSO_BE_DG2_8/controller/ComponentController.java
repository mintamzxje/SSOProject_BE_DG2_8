package com.sso.SSO_BE_DG2_8.controller;

import com.sso.SSO_BE_DG2_8.dto.ComponentDTO;
import com.sso.SSO_BE_DG2_8.model.Component;
import com.sso.SSO_BE_DG2_8.service.impl.ComponentServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/component")
public class ComponentController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ComponentServiceImpl componentService;
    @GetMapping("/all")
    public List<ComponentDTO> getAllComponent(){
        return componentService.getAllComponent().stream()
                .map(component -> modelMapper.map(component, ComponentDTO.class))
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ComponentDTO>> getComponentById(@PathVariable(name = "id") String id){
        Optional<Component> component = componentService.getComponentById(id);
        Optional<ComponentDTO> componentDTO = Optional.ofNullable(modelMapper.map(component, ComponentDTO.class));
        return ResponseEntity.ok().body(componentDTO);
    }
    @PostMapping("/create")
    public ResponseEntity<ComponentDTO> createNewComponent(@RequestBody ComponentDTO componentDTO){
        Component componentRequest = modelMapper.map(componentDTO , Component.class);
        Component component = componentService.createComponent(componentRequest);
        ComponentDTO componentResponse = modelMapper.map(component, ComponentDTO.class);
        return ResponseEntity.ok().body(componentResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ComponentDTO> updateComponent(@PathVariable(name = "id") String id,ComponentDTO componentDTO){
        Component componentRequest = modelMapper.map(componentDTO, Component.class);
        Component component = componentService.updateComponent(componentRequest, id);
        ComponentDTO componentResponse = modelMapper.map(component, ComponentDTO.class);
        return ResponseEntity.ok().body(componentResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ComponentDTO> deleteComponent(@PathVariable(name = "id") String id){
        componentService.deleteComponent(id);
        return ResponseEntity.ok().build();
    }
}
