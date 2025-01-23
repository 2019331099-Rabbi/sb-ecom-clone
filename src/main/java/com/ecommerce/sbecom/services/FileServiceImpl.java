package com.ecommerce.sbecom.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(MultipartFile image, String uploadDirectory) throws IOException {
        String originalFileName = image.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();
        String fileNameExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String fileName = uuid.concat(fileNameExtension);
        String filePath = uploadDirectory + File.separator + fileName;

        File folder = new File(uploadDirectory);
        if (!folder.exists()) {
            folder.mkdir();
        }

        Files.copy(image.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
