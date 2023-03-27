package com.sso.controller;

import com.sso.model.Component;
import com.sso.payload.dto.ComponentDTO;
import com.sso.payload.response.ResponseDTO;
import com.sso.service.impl.V2__ComponentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/component_v2")
public class V2__ComponentController {
    @Autowired
    private V2__ComponentServiceImpl v2__componentService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllComponent(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO(true, HttpStatus.OK, "", v2__componentService.getAllComponent())
        );
    }
}
