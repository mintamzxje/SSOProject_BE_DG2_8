package com.sso.factory.excel;

import com.sso.exception.NotFoundException;
import com.sso.model.Component;
import com.sso.model.User;
import com.sso.repository.ComponentRepository;
import com.sso.repository.UserRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ExcelHelper {
    private final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final String SHEET = "Users";
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private UserRepository userRepository;

        public boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public Set<User> importExcelUserToComponent(InputStream is, String uuid) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            Set<User> excelUsers = new HashSet<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                User user = new User();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            user.setUuid(currentCell.getStringCellValue());
                            break;
                        case 1:
                            user.setUserName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            user.setFullName(currentCell.getStringCellValue());
                            break;
                        case 3:
                            user.setFirstName(currentCell.getStringCellValue());
                            break;
                        case 4:
                            user.setLastName(currentCell.getStringCellValue());
                            break;
                        case 5:
                            user.setPhone(currentCell.getStringCellValue());
                            break;
                        case 6:
                            user.setEmail(currentCell.getStringCellValue());
                            break;
                        case 7:
                            user.setAddress(currentCell.getStringCellValue());
                            break;
                        case 8:
                            user.setAvatar(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }

                User us = userRepository.findById(user.getUuid())
                        .orElseThrow(() -> new NotFoundException("Not Found User UUID: " + user.getUuid()));
                user.setPassWord(us.getPassWord());

                Component component = componentRepository.findById(uuid)
                        .orElseThrow(() -> new NotFoundException("Not Found Component UUID: " + uuid));

                Set<User> userSet = component.getUsers();
                if(!user.getUuid().equals("") && user.getUserName() != null){
                    excelUsers.add(user);
                    for (User setUser : userSet){
                        for (User excelUser : excelUsers){
                            if(setUser.getUuid().equals(excelUser.getUuid())){
                                excelUsers.remove(user);
                            }
                        }
                    }
                }
            }
            workbook.close();
            return excelUsers;
        } catch (IOException e) {
            throw new RuntimeException("Fail To Parse Excel File: " + e.getMessage());
        }
    }
}

