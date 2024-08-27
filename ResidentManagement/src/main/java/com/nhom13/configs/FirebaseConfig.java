/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 *
 * @author ADMIN
 */
@Configuration
public class FirebaseConfig {
    
    @Value("classpath:residentmanagement-4500e-firebase-adminsdk-90wk3-92460aa80d.json")
    private Resource serviceAccountResource;
    
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        
        
       try (InputStream  serviceAccount =  this.serviceAccountResource.getInputStream()) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

            return FirebaseApp.initializeApp(options);
        }
    }
}
