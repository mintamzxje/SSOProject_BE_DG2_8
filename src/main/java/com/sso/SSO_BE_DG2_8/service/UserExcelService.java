package com.sso.SSO_BE_DG2_8.service;

import com.sso.SSO_BE_DG2_8.model.Component;
import com.sso.SSO_BE_DG2_8.model.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserExcelService {
    private XSSFWorkbook workbook;
    private Sheet sheet;

    //need userRepository
    private Component componentNeedexport;

    public UserExcelService(){
        componentNeedexport = new Component(
                "Home",
                "200",
                "smil",
                new HashSet()
        );
        componentNeedexport.getUsers().add(new User(
                UUID.randomUUID().toString(),
                "admin",
                "admin",
                "Nguyen A",
                "A",
                "Nguyen",
                "1231231287",
                "NguyenA@gmail.com",
                "12 Quy nhon",
                "/avatar1.png"
        ));
        componentNeedexport.getUsers().add(new User(
                UUID.randomUUID().toString(),
                "admin",
                "admin",
                "Nguyen B",
                "B",
                "Nguyen",
                "1231231287",
                "NguyenB@gmail.com",
                "12 Quy nhon",
                "/avatar2.png"
        ));
        componentNeedexport.getUsers().add(new User(
                UUID.randomUUID().toString(),
                "admin",
                "admin",
                "Nguyen C",
                "C",
                "Nguyen",
                "1231231287",
                "NguyenC@gmail.com",
                "12 Quy nhon",
                "/avatar3.png"
        ));
    }

    private void createSheet(){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Users from component");
        Row row = sheet.createRow(0);
        //style
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        // cells
        Cell cell = row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("Id");

        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("Username");

        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue("Password");

        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell.setCellValue("Full Name");

        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell.setCellValue("First Name");

        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell.setCellValue("Last Name");

        cell = row.createCell(6);
        cell.setCellStyle(style);
        cell.setCellValue("Phone");

        cell = row.createCell(7);
        cell.setCellStyle(style);
        cell.setCellValue("Email");

        cell = row.createCell(8);
        cell.setCellStyle(style);
        cell.setCellValue("Address");

        cell = row.createCell(9);
        cell.setCellStyle(style);
        cell.setCellValue("Avatar");
    }

    public void exportUsersFromComponent(HttpServletResponse response) throws IOException {
        createSheet();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


}
