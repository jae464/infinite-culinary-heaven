package com.culinaryheaven.domain.image.controller;

import com.culinaryheaven.domain.image.domain.ImageStorageClient;
import com.culinaryheaven.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageStorageClient imageStorageClient;

    @PostMapping
    public ResponseEntity<Void> saveImage(
            @RequestPart MultipartFile file
    ) {
        String path = imageStorageClient.uploadImage(file);
        System.out.println(path);
        return ResponseEntity.ok().build();
    }
}
