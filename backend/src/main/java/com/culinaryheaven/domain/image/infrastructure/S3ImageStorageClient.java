package com.culinaryheaven.domain.image.infrastructure;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.culinaryheaven.domain.image.ImageStorageClient;
import com.culinaryheaven.global.exception.CustomException;
import com.culinaryheaven.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@Primary
@RequiredArgsConstructor
public class S3ImageStorageClient implements ImageStorageClient {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String IMAGE_PATH = "image/";

    @Override
    public String uploadImage(MultipartFile file) {

        if (file == null) { return null; }
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            String contentType = file.getContentType();
            ObjectMetadata metadata= new ObjectMetadata();

            metadata.setContentType(contentType);
            metadata.setContentLength(file.getSize());

            s3Client.putObject(new PutObjectRequest(bucket, IMAGE_PATH + fileName, file.getInputStream(), metadata));

        } catch (AmazonS3Exception e) {
            throw new CustomException(ErrorCode.IMAGE_SAVE_FAILED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return s3Client.getUrl(bucket, IMAGE_PATH + fileName).toString();
    }

    @Override
    public Resource loadImage(String image) {
        return null;
    }
}
