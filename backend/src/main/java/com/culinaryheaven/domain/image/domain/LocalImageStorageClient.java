package com.culinaryheaven.domain.image.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class LocalImageStorageClient implements ImageStorageClient {

    @Value("${image.store.dir}")
    private String imageStoragePath;
//    private String imageStoragePath = "/Users/jae464/Desktop/workspace/07_infinite-culinary-heaven/backend/";

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(imageStoragePath, fileName);

            File newFile = new File(imageStoragePath + fileName);
            file.transferTo(filePath.toFile());
            System.out.println(filePath.toAbsolutePath());
            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
