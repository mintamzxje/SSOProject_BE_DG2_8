package com.sso.service;

import com.sso.model.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserExcelService {
    void exportUsersFromComponent(HttpServletResponse response) throws IOException;
    List<User> importUsersFromExcel(MultipartFile file, String componentId) throws IOException;
}
