/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.services.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.nhom13.services.NotificationService;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class NotificationServiceImpl implements NotificationService{
    @Override
    public void sendNotification(String token, String title, String body) {
        
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .setImage("https://res.cloudinary.com/dwdvnztnn/image/upload/v1716542240/2_k3qyxl.png")
                .build();
        
        Message message = Message.builder()
                .setNotification(notification)
                .setToken(token)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
