package com.sso.controller;

import com.sso.doc.MailMergeNotification;
import com.sso.dto.ComponentDTO;
import com.sso.dto.response.ResponseDTO;
import com.sso.dto.request.AddUserToComponentRequest;
import com.sso.service.impl.ComponentServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.util.List;


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
    @PostMapping("/adduser/{id}")
    @ApiOperation(value = "Add User To Component", response = ResponseEntity.class)
    public ResponseEntity<?> addUserToComponent(@PathVariable(name = "id") String id,@RequestBody AddUserToComponentRequest user){
        componentService.addUserToComponent(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO("","add success")
        );
    }
    @Autowired
    MailMergeNotification mailMergeNotification;
    @PostMapping("/mailMergeNotification/{id}")
    public ResponseEntity<Resource> mailMergeNotification(@PathVariable(name = "id") String uuid) throws Exception {
        String pdfPath = "";
        try {
            pdfPath = mailMergeNotification.MailMergeData(uuid);
            File file = new File(pdfPath);
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

            HttpHeaders headers = new HttpHeaders();
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=list-component.pdf";
            headers.set(headerKey, headerValue);

            return ResponseEntity.ok().headers(headers).contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
        } finally {
            File file = new File(pdfPath);
            Files.delete(file.toPath());
        }
    }
}
