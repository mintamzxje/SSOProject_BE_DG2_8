package com.sso.controller;

import com.sso.dto.ComponentDTO;
import com.sso.dto.response.ResponseDTO;
import com.sso.model.Component;
import com.sso.dto.request.AddUserToComponentRequest;
import com.sso.service.impl.ComponentServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/component")
public class ComponentController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ComponentServiceImpl componentService;
    @GetMapping("/all")
    @ApiOperation(value = "Get All Component", response = List.class)
    public List<ComponentDTO> getAllComponent(){
        return componentService.getAllComponent().stream()
                .map(component -> modelMapper.map(component, ComponentDTO.class))
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Get Component By Id", response = ResponseEntity.class)
    public ResponseEntity<ComponentDTO> getComponentById(@PathVariable(name = "id") String id){
        Component component = componentService.getComponentById(id);
        ComponentDTO componentDTO = modelMapper.map(component, ComponentDTO.class);
        return ResponseEntity.ok().body(componentDTO);
    }
    @PostMapping("/create")
    @ApiOperation(value = "Create New Component", response = ResponseEntity.class)
    public ResponseEntity<ComponentDTO> createNewComponent(@RequestBody ComponentDTO componentDTO){
        Component componentRequest = modelMapper.map(componentDTO , Component.class);
        Component component = componentService.createComponent(componentRequest);
        ComponentDTO componentResponse = modelMapper.map(component, ComponentDTO.class);
        return ResponseEntity.ok().body(componentResponse);
    }
    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update Component", response = ResponseEntity.class)
    public ResponseEntity<ComponentDTO> updateComponent(@PathVariable(name = "id") String id,ComponentDTO componentDTO){
        Component componentRequest = modelMapper.map(componentDTO, Component.class);
        Component component = componentService.updateComponent(componentRequest, id);
        ComponentDTO componentResponse = modelMapper.map(component, ComponentDTO.class);
        return ResponseEntity.ok().body(componentResponse);
    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete Component", response = ResponseEntity.class)
    public ResponseEntity<?> deleteComponent(@PathVariable(name = "id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO("","delete success",componentService.deleteComponent(id))
        );
    }
    @PostMapping("/adduser/{id}")
    @ApiOperation(value = "Add User To Component", response = ResponseEntity.class)
    public ResponseEntity<?> addUserToComponent(@PathVariable(name = "id") String id,@RequestBody AddUserToComponentRequest user){
        Component component = componentService.getComponentById(id);
        component.setUsers(user.getUses());
        componentService.addUserToComponent(component);
        //return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO("","add success")
        );
    }
}
