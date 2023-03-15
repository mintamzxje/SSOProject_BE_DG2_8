package com.sso.controller;

import com.sso.service.UserExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/api/utils/")
public class UtilsController {
    @Autowired
    UserExcelService userExcelService;

    @GetMapping("/export/{componentId}")
    public void exportUsers(HttpServletResponse response, @PathVariable String componentId){
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=book_" + new Date().toString() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        try {
            userExcelService.exportUsersFromComponent(response, componentId);
        }
        catch (Exception e){
            System.out.println("error when import excel");
        }
    }
}

