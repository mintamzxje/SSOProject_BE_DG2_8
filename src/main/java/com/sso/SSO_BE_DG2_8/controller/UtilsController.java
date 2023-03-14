package com.sso.SSO_BE_DG2_8.controller;

import com.sso.SSO_BE_DG2_8.model.User;
import com.sso.SSO_BE_DG2_8.service.UserExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
public class UtilsController {
    @Autowired
    UserExcelService userExcelService;
    @GetMapping("/a")
    public String a(){
        return "hello";
    }


    @GetMapping("/utils/export/{componentId}")
    public void exportUsers(HttpServletResponse response, String componentId){
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=book_" + new Date().toString() + ".xlsx";
        response.setHeader(headerKey, headerValue);
        try {
            userExcelService.exportUsersFromComponent(response);
        }
        catch (Exception e){
            System.out.println("error when import excel");
        }
    }
}
