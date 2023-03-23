package com.sso.controller;

import com.sso.doc.MailMergeNotification;
import com.sso.exception.NotFoundException;
import com.sso.payload.dto.ComponentDTO;
import com.sso.payload.response.ResponseDTO;
import com.sso.payload.request.AddUserToComponentRequest;
import com.sso.service.impl.ComponentServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @GetMapping("/{uuid}")
    @ApiOperation(value = "Get Component By UUID", response = ResponseEntity.class)
    public ResponseEntity<?> getComponentByUUID(
            @ApiParam(value = "UUID of the Component", required = true)
            @PathVariable(name = "uuid") String uuid){

        if(!componentService.existsById(uuid))
        {
            throw new NotFoundException("UUID Not Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK, "", componentService.getComponentById(uuid))
        );
    }
    @PostMapping(value = "/create", consumes = "multipart/form-data")
    @ApiOperation(value = "Create New Component", response = ResponseEntity.class)
    public ResponseEntity<?> createNewComponent(
            @ApiParam(value = "The component object that needs to be created ", required = true)
            @ModelAttribute ComponentDTO componentRequest,
            @ApiParam(value = "Icon of the Component", required = true)
            @RequestPart(name = "file") MultipartFile file){

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO(true, HttpStatus.CREATED, "",
                        componentService.createComponent(componentRequest, file))
        );
    }
    @PutMapping(value = "/update/{uuid}", consumes = "multipart/form-data")
    @ApiOperation(value = "Update Component", response = ResponseEntity.class)
    public ResponseEntity<?> updateComponent(
            @ApiParam(value = "UUID of the Component", required = true)
            @PathVariable(name = "uuid") String uuid,
            @ApiParam(value = "The component object that needs to be created ", required = true)
            @ModelAttribute ComponentDTO componentRequest,
            @ApiParam(value = "Icon of the Component", required = true)
            @RequestPart(name = "file") MultipartFile file){

        if(componentService.updateComponent(componentRequest, uuid, file) != null){
            ComponentDTO componentDTO = componentService.getComponentById(uuid);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO(true, HttpStatus.OK, "", componentDTO)
            );
        }else {
            throw new NotFoundException("UUID Not Found");
        }
    }
    @DeleteMapping("/delete/{uuid}")
    @ApiOperation(value = "Delete Component", response = ResponseEntity.class)
    public ResponseEntity<?> deleteComponent(
            @ApiParam(value = "UUID of the Component", required = true)
            @PathVariable(name = "uuid") String uuid){

        if(!componentService.existsById(uuid))
        {
            throw new NotFoundException("UUID Not Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK, "", componentService.deleteComponent(uuid))
        );
    }
    @PostMapping(value = "/{uuid}/adduser", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add User To Component", response = ResponseEntity.class)
    public ResponseEntity<?> addUserToComponent(
            @ApiParam(value = "UUID of the Component", required = true)
            @PathVariable(name = "uuid") String uuid,
            @ApiParam(value = "The user object that needs to be add ")
            @RequestBody AddUserToComponentRequest user){

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK, "null",
                        componentService.addUserToComponent(uuid, user))
        );
    }
    @GetMapping("/get-list-component-for-user/{uuid}")
    @ApiOperation(value = "Get All List Components For User", response = ResponseEntity.class)
    public ResponseEntity<?> getListComponentByUserUuid(
            @ApiParam(value = "UUID of the User", required = true)
            @PathVariable(name = "uuid") String uuid){

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK, "",
                        componentService.getComponentByUserUuid(uuid))
        );
    }
    @GetMapping("/get-list-user-in-component/{uuid}")
    @ApiOperation(value = "Get All List User For Component", response = ResponseEntity.class)
    public ResponseEntity<?> getListUserInComponent(
            @ApiParam(value = "UUID of the Component", required = true)
            @PathVariable(name = "uuid") String uuid){

        if(!componentService.existsById(uuid))
        {
            throw new NotFoundException("UUID Not Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK, "",
                        componentService.getAllUserInComponent(uuid))
        );
    }
    @PostMapping("/mailMergeNotification/{uuid}")
    @ApiOperation(value = "Mail Merge Notification List Component For User", response = ResponseEntity.class)
    public ResponseEntity<Resource> mailMergeNotification(
            @ApiParam(value = "UUID of the User", required = true)
            @PathVariable(name = "uuid") String uuid) throws Exception {

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
