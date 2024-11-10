package com.culinaryheaven.domain.image.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class LocalImageStorageClient implements ImageStorageClient {

    @Value("${image.store.dir}")
    private String imageStoragePath;

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            Path storageDir = Paths.get(imageStoragePath);

            if (Files.notExists(storageDir)) {
                Files.createDirectories(storageDir);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = storageDir.resolve(fileName);

            file.transferTo(filePath.toFile());
            System.out.println("File saved at: " + filePath.toAbsolutePath());

            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
