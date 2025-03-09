package com.culinaryheaven.domain.image;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageClient {
    String uploadImage(MultipartFile file);
    Resource loadImage(String image);
}
