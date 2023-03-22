package com.sso.factory.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilesStorageService {
    private String RootPath = "uploads";
    private Path convertRelativeToAbsolutePath(String relativePath) throws Exception{
        return Paths.get(RootPath + relativePath);
    }
    public void saveAs(MultipartFile file, String relativePath){
        try {
            File directory = new File(convertRelativeToAbsolutePath(relativePath).getParent().toString());
            if(!directory.exists()){
                directory.mkdirs();
            }
            Files.copy(file.getInputStream(), convertRelativeToAbsolutePath(relativePath));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error:" + e.getMessage());
        }
    }
    public boolean delete(String filename, String relativePath) {
        try {
            Path file = convertRelativeToAbsolutePath(relativePath).resolve(filename);
            return Files.deleteIfExists(file);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
