package com.sso.controller;

import com.sso.dto.ComponentDTO;
import com.sso.dto.response.ResponseDTO;
import com.sso.model.Component;
import com.sso.dto.request.AddUserToComponentRequest;
import com.sso.service.impl.ComponentServiceImpl;
import io.swagger.annotations.ApiOperation;
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
    private ComponentServiceImpl componentService;
    @GetMapping("/all")
    @ApiOperation(value = "Get All Component", response = List.class)
    public List<ComponentDTO> getAllComponent(){
        return componentService.getAllComponent();
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Get Component By Id", response = ResponseEntity.class)
    public ResponseEntity<?> getComponentById(@PathVariable(name = "id") String id){
        ComponentDTO componentDTO = componentService.getComponentById(id);
        return ResponseEntity.ok().body(componentDTO);
    }
    @PostMapping("/create")
    @ApiOperation(value = "Create New Component", response = ResponseEntity.class)
    public ResponseEntity<?> createNewComponent(@RequestBody ComponentDTO componentRequest){
        ComponentDTO componentResponse = componentService.createComponent(componentRequest);
        return ResponseEntity.ok().body(componentResponse);
    }
    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update Component", response = ResponseEntity.class)
    public ResponseEntity<?> updateComponent(@PathVariable(name = "id") String id, @RequestBody ComponentDTO componentRequest){
        if(componentService.updateComponent(componentRequest, id) != null){
            ComponentDTO componentDTO = componentService.getComponentById(id);
            return ResponseEntity.ok().body(componentDTO);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete Component", response = ResponseEntity.class)
    public ResponseEntity<?> deleteComponent(@PathVariable(name = "id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO("","delete success", componentService.deleteComponent(id))
        );
    }
}
