package com.culinaryheaven.domain.image.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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

            Files.write(filePath, file.getBytes());
            System.out.println("File saved at: " + filePath.toAbsolutePath());

            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("이미지 저장에 실패했습니다.", e);
        }
    }

    @Override
    public Resource loadImage(String image) {
        try {

            Path filePath = Paths.get(imageStoragePath, image);
            String fullPath = filePath.toAbsolutePath().toString();
            return new UrlResource("file:", fullPath);

        } catch(MalformedURLException e) {
            throw new RuntimeException("파일 경로를 확인해주세요.");
        }
    }

}
