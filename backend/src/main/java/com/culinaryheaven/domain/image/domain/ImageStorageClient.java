package com.culinaryheaven.domain.image.domain;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageClient {
    String uploadImage(MultipartFile file);
}
