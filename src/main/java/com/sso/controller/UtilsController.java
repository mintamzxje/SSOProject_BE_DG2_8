package com.sso.controller;

import com.sso.doc.MailMergeNotification;
import com.sso.service.UserExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
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

