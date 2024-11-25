package com.culinaryheaven.domain.image.domain;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class S3ImageStorageClient implements ImageStorageClient {
    @Override
    public String uploadImage(MultipartFile file) {
        return "";
    }

    @Override
    public Resource loadImage(String image) {
        return null;
    }
}
