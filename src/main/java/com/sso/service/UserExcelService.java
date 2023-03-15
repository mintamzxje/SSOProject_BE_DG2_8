package com.sso.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserExcelService {
    void exportUsersFromComponent(HttpServletResponse response, String componentId) throws IOException;
}
