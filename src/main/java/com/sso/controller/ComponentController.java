package com.sso.controller;

import com.sso.doc.MailMergeNotification;
import com.sso.payload.dto.ComponentDTO;
import com.sso.payload.response.ResponseDTO;
import com.sso.payload.request.AddUserToComponentRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;


@RestController
@RequestMapping("/api/component")
public class ComponentController {
    @Autowired
    private ComponentServiceImpl componentService;
    @Autowired
    MailMergeNotification mailMergeNotification;

    @GetMapping("/all")
    @ApiOperation(value = "Get All Component", response = ResponseEntity.class)
    public ResponseEntity<?> getAllComponent(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK, "", componentService.getAllComponent()));
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Get Component By Id", response = ResponseEntity.class)
    public ResponseEntity<?> getComponentById(@PathVariable(name = "id") String id){
        if(!componentService.existsById(id))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO(false, HttpStatus.NOT_FOUND, "ID Not Found", null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK, "", componentService.getComponentById(id))
        );
    }
    @PutMapping(value = "/create", consumes = "multipart/form-data")
    @ApiOperation(value = "Create New Component", response = ResponseEntity.class)
    public ResponseEntity<?> createNewComponent(@ModelAttribute ComponentDTO componentRequest,
                                                @RequestPart(name = "file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO(true, HttpStatus.CREATED, "",
                        componentService.createComponent(componentRequest, file))
        );
    }
    @PostMapping(value = "/update/{id}", consumes = "multipart/form-data")
    @ApiOperation(value = "Update Component", response = ResponseEntity.class)
    public ResponseEntity<?> updateComponent(@PathVariable(name = "id") String id,
                                             @ModelAttribute ComponentDTO componentRequest,
                                             @RequestPart(name = "file") MultipartFile file){
        if(componentService.updateComponent(componentRequest, id, file) != null){
            ComponentDTO componentDTO = componentService.getComponentById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO(true, HttpStatus.OK, "", componentDTO)
            );
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO(false, HttpStatus.BAD_REQUEST, "ID Not Found", id)
            );
        }
    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete Component", response = ResponseEntity.class)
    public ResponseEntity<?> deleteComponent(@PathVariable(name = "id") String id){
        if(!componentService.existsById(id))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO(false, HttpStatus.BAD_REQUEST, "ID Not Found", null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK, "", componentService.deleteComponent(id))
        );
    }
    @PostMapping("/{id}/adduser")
    @ApiOperation(value = "Add User To Component", response = ResponseEntity.class)
    public ResponseEntity<?> addUserToComponent(@PathVariable(name = "id") String id,
                                                @RequestBody AddUserToComponentRequest user){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK, "null",
                        componentService.addUserToComponent(id, user))
        );
    }
    @GetMapping("/get-list-component-for-user/{id}")
    @ApiOperation(value = "Get All List Components For User", response = ResponseEntity.class)
    public ResponseEntity<?> getListComponentByUserUuid(@PathVariable(name = "id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK, "",
                        componentService.getComponentByUserUuid(id))
        );
    }
    @GetMapping("/get-list-user-in-component/{id}")
    @ApiOperation(value = "Get All List User For Component", response = ResponseEntity.class)
    public ResponseEntity<?> getListUserInComponent(@PathVariable(name = "id") String id){
        if(!componentService.existsById(id))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
              new ResponseDTO(false, HttpStatus.BAD_REQUEST, "ID Not Found", null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK, "",
                        componentService.getAllUserInComponent(id))
        );
    }
    @PostMapping("/mailMergeNotification/{id}")
    @ApiOperation(value = "Mail Merge Notification List Component For User", response = ResponseEntity.class)
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
