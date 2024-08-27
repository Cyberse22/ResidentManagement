/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.services.impl;

import com.nhom13.pojo.Feedback;
import com.nhom13.repositories.FeedbackRepository;
import com.nhom13.services.FeedbackService;
import com.nhom13.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class FeedbackServiceImpl implements FeedbackService{
    
    @Autowired
    private FeedbackRepository feedbackRepo;
    @Autowired
    private UserService userService;

    @Override
    public List<Object[]> loadFeedbacks(Map<String, String> params) {
        return this.feedbackRepo.loadFeedbacks(params);
    }

    @Override
    public void solveFeedback(int id) {
        this.feedbackRepo.solveFeedback(id);
    }

    @Override
    public Feedback getFeedbackById(int id) {
        return this.feedbackRepo.getFeedbackById(id);
    }

    @Override
    public void deleteFeedback(int id) {
        this.feedbackRepo.deleteFeedback(id);
    }

    @Override
    public void createFeedback(Feedback feedback) {
        int userId = this.userService.getCurrentUser().getId();
        this.feedbackRepo.createFeedback(feedback, userId);
        
    }
    
}
