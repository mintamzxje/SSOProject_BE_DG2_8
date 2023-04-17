package com.sso.service.impl;

import com.sso.model.Component;
import com.sso.model.User;
import com.sso.repository.ComponentRepository;
import com.sso.repository.UserRepository;
import com.sso.service.UserExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
public class UserExcelServiceImpl implements UserExcelService {
    private XSSFWorkbook workbook;
    private Sheet sheet;
    private String sheetName = "Users";

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private UserRepository userRepository;


    private void createSheetFromComponent(List<User> users){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);
        //style
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        // cell

        Cell cell = row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("Username");

        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("Full Name");

        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue("First Name");

        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell.setCellValue("Last Name");

        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell.setCellValue("Phone");

        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell.setCellValue("Email");

        cell = row.createCell(6);
        cell.setCellStyle(style);
        cell.setCellValue("Address");

        cell = row.createCell(7);
        cell.setCellStyle(style);
        cell.setCellValue("Avatar");
        //
        int i = 1;
        for (User user: users) {
            row = sheet.createRow(i); i++;

            cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue(user.getUserName());

            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue(user.getFullName());

            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(user.getFirstName());

            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue(user.getLastName());

            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue(user.getPhone());

            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue(user.getEmail());

            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue(user.getAddress());

            cell = row.createCell(7);
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

    }

    public void exportUsersFromComponent(HttpServletResponse response) throws IOException {
        createSheetFromComponent(userRepository.findAll());
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public List<User> importUsersFromExcel(MultipartFile file, String componentId) throws IOException{
        if(!componentRepository.existsById(componentId)){
            return null;
        }

        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        Sheet sheet = workbook.getSheet(sheetName);
        Iterator<Row> rows = sheet.iterator();

        Component component = componentRepository.findById(componentId).get();
        List<User> excelUsers = new ArrayList();

        int rowNumber = 0;
        while(rows.hasNext()){
            Row currentRow = rows.next();

            // skip header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }

            Iterator<Cell> cellsInRow = currentRow.iterator();
            User user = new User();

            int cellIdx = 1;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();
                switch (cellIdx) {
                    case 1:
                        user.setUserName(currentCell.getStringCellValue());
                        break;
                    case 2:
                        user.setPassWord(currentCell.getStringCellValue());
                        break;
                    case 3:
                        user.setFullName(currentCell.getStringCellValue());
                        break;
                    case 4:
                        user.setFirstName(currentCell.getStringCellValue());
                        break;
                    case 5:
                        user.setLastName(currentCell.getStringCellValue());
                        break;
                    case 6:
                        user.setPhone(currentCell.getStringCellValue());
                        break;
                    case 7:
                        user.setEmail(currentCell.getStringCellValue());
                        break;
                    case 8:
                        user.setAddress(currentCell.getStringCellValue());
                        break;
                    case 9:
                        user.setAvatar(currentCell.getStringCellValue());
                        break;
                    default:
                        break;
                }
                cellIdx++;
            }
            if(userRepository.existsByUserName(user.getUserName())){
                continue;
            }
            if(userRepository.existsByEmail(user.getEmail())){
                continue;
            }
            excelUsers.add(user);
            component.getUsers().add(user);
        }
        workbook.close();
        userRepository.saveAll(excelUsers);
        componentRepository.save(component);

        return excelUsers;
    }
}
