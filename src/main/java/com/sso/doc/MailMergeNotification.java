package com.sso.doc;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.sso.dto.ComponentDTO;
import com.sso.dto.UserDTO;
import com.sso.mapper.ComponentMapper;
import com.sso.model.Component;
import com.sso.service.impl.ComponentServiceImpl;
import com.sso.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MailMergeNotification {
    public static final Locale LOCALE = new Locale("id", "ID");
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy", LOCALE);
    public static final NumberFormat NUMBER_FORMAT = NumberFormat.getCurrencyInstance(LOCALE);

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ComponentServiceImpl componentService;

    public String MailMergeData(String uuid_user) throws Exception {
        UserDTO userDTO = userService.getOneUser(uuid_user);
        List<ComponentDTO> componentDTOs = componentService.getAllComponent();
        List<Component> components = ComponentMapper.MAPPER.mapListToComponent(componentDTOs);
        Component component_ = new Component();
        for (Component component1 : components) {
            if (component1.getUsers() == userDTO) {
                component_.setUuid(component1.getUuid());
                component_.setName(component1.getName());
                component_.setCode(component1.getCode());
            }
            else {
                return "abc";
            }
        }

        String pdfFileName = "D:/Receipt.pdf";
        String[] fieldNames = new String[]{
                "user",
                "uuid_user",
                "userName",
                "email",
                "fullName",
                "component",
                "uuid_component",
                "name",
                "code"
        };
        String[] fieldValues = new String[]{
                "User Information",
                userDTO.getUuid(),
                userDTO.getUserName(),
                userDTO.getEmail(),
                userDTO.getFullName(),
                "Component Information",
                component_.getUuid(),
                component_.getName(),
                component_.getCode()
        };
        Document document = new Document();
        document.loadFromStream(MailMergeNotification.class.getResourceAsStream("/templates/docs/list-component.docx"), FileFormat.Auto);
        document.getMailMerge().execute(fieldNames, fieldValues);
        document.saveToFile(pdfFileName, FileFormat.PDF);
        return pdfFileName;
    }
}
