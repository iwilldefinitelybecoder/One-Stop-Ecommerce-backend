package com.Onestop.ecommerce.utils;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {


    public List<MultipartFile> retrieveFiles(List<String> fileNames) throws IOException {
        String directoryPath = "C:/Users/tonys/Downloads/product-Images/";
        List<MultipartFile> responseFiles = new ArrayList<>();

        for (String fileName : fileNames) {
            File file = new File(directoryPath + fileName);

            if (file.exists()) {
                FileInputStream input = new FileInputStream(file);
                MultipartFile multipartFile = new MockMultipartFile(
                        file.getName(),
                        file.getName(),
                        "image/jpeg", // Replace with the appropriate content type
                        input
                );
                responseFiles.add(multipartFile);
            }
        }

        return responseFiles;
    }
}
