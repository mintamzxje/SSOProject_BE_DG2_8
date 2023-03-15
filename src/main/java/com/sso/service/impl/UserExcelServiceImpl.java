package com.sso.service.impl;

import com.sso.model.Component;
import com.sso.model.User;
import com.sso.repository.ComponentRepository;
import com.sso.service.UserExcelService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserExcelServiceImpl implements UserExcelService {
    private XSSFWorkbook workbook;
    private Sheet sheet;

    @Autowired
    private ComponentRepository componentRepository;

    private void createSheetFromComponent(Component component){
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
        //
        int i = 1;
        for (User user:component.getUsers()) {
            row = sheet.createRow(i); i++;

            cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue(user.getUuid());

            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue(user.getUserName());

            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(user.getPassWord());

            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue(user.getFullName());

            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue(user.getFirstName());

            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue(user.getLastName());

            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue(user.getPhone());

            cell = row.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue(user.getEmail());

            cell = row.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue(user.getAddress());

            cell = row.createCell(9);
            cell.setCellStyle(style);
            cell.setCellValue(user.getAvatar());
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(9);

    }

    public void exportUsersFromComponent(HttpServletResponse response, String componentId) throws IOException {
        Component component = componentRepository.findById(componentId).orElse(null);
        if(component == null) return;
        createSheetFromComponent(component);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
