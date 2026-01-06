package com.motiolab.nabusi_server;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class FCMInitializer {
    @Value("${firebase.file.path}")
    private String firebaseFilePath;

    @Value("${firebase.project-id}")
    private String projectId;

    @PostConstruct
    public void initialize() {
        log.info("🚀 Initializing Firebase with file: {} and Project ID: {}", firebaseFilePath, projectId);
        try (InputStream serviceAccount = new ClassPathResource(firebaseFilePath).getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(projectId)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("🔥 Firebase (default) has been initialized successfully. Project ID: {}",
                        options.getProjectId());
            } else {
                log.info("ℹ️ Firebase already initialized. Existing apps: {}. Default Project ID: {}",
                        FirebaseApp.getApps().stream().map(FirebaseApp::getName).toList(),
                        FirebaseApp.getInstance().getOptions().getProjectId());
            }
        } catch (IOException e) {
            log.error("❌ Firebase initialization failed: {}", e.getMessage(), e);
            throw new RuntimeException("🔥 Firebase initialization failed: " + e.getMessage());
        }
    }
}
