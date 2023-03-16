package com.sso.doc;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.reporting.MailMergeDataTable;
import com.sso.payload.dto.ComponentDTO;
import com.sso.payload.dto.UserDTO;
import com.sso.service.impl.ComponentServiceImpl;
import com.sso.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailMergeNotification {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ComponentServiceImpl componentService;

    public String MailMergeData(String uuid_user) throws Exception {
        String pdfFileName = "D:/Receipt.pdf";
        UserDTO userDTO = userService.getOneUser(uuid_user);
        List<ComponentDTO> components = componentService.getComponentByUserUuid(uuid_user);
        String[] fieldNames = new String[]{
                "uuid_user",
                "userName",
                "fullName",
                "email"
        };
        String[] fieldValues = new String[]{
                userDTO.getUuid(),
                userDTO.getUserName(),
                userDTO.getFullName(),
                userDTO.getEmail()
        };
        Document document = new Document();
        document.loadFromStream(MailMergeNotification.class.getResourceAsStream("/templates/docs/list-component.docx"), FileFormat.Auto);
        MailMergeDataTable dataTable = new MailMergeDataTable("Components", components);
        document.getMailMerge().execute(fieldNames,fieldValues);
        document.getMailMerge().executeGroup(dataTable);
        document.saveToFile(pdfFileName, FileFormat.PDF);
        return pdfFileName;
    }
}
