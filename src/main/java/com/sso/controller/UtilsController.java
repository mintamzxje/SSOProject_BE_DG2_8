package com.sso.controller;

import com.sso.model.User;
import com.sso.service.UserExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/utils/")
public class UtilsController {
    @Autowired
    UserExcelService userExcelService;

    @GetMapping("/export")
    public void exportUsers(HttpServletResponse response){
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=book_" + new Date().toString() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        try {
            userExcelService.exportUsersFromComponent(response);
        }
        catch (Exception e){
            System.out.println("error when export excel");
        }
    }

    @PostMapping("/import/{componentId}")
    public List<User> importUsers(@RequestPart MultipartFile file, @PathVariable String componentId){
        List<User> result = null;
        try{
            result = userExcelService.importUsersFromExcel(file, componentId);
        }
        catch (Exception e){
            System.out.println("error when import excel");
        }

        return result;
    }
}

