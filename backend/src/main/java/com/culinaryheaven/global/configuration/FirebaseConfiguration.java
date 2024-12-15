package com.culinaryheaven.global.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@Configuration
public class FirebaseConfiguration {

    @Value("${firebase.private-key-path}")
    private String firebasePrivateKeyPath;

    private final ResourceLoader resourceLoader;


    public FirebaseConfiguration(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public FirebaseApp firebaseApp() throws IOException {
        Resource resource = resourceLoader.getResource(firebasePrivateKeyPath);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                .build();

        return FirebaseApp.initializeApp(options);
    }

}
