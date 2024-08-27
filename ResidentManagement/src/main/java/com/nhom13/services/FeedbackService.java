/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.services;

import com.nhom13.pojo.Feedback;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public interface FeedbackService {
    List<Object[]> loadFeedbacks (Map<String, String> params);
    void solveFeedback (int id); //chuyen status -> 0
    Feedback getFeedbackById (int id);
    void deleteFeedback (int id);  //xoa luon
    void createFeedback (Feedback feedback);
}
