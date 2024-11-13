package com.culinaryheaven.domain.image.domain;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageClient {
    String uploadImage(MultipartFile file);
    Resource loadImage(String image);
}
