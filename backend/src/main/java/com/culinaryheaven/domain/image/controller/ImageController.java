package com.culinaryheaven.domain.image.controller;

import com.culinaryheaven.domain.image.domain.ImageStorageClient;
import com.culinaryheaven.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity<Resource> getImage(
            @RequestParam  String image
    ) {
        Resource res = imageStorageClient.loadImage(image);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(res, headers, HttpStatus.OK);

    }
}
