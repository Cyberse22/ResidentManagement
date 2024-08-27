/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.services.impl;

import com.nhom13.pojo.Question;
import com.nhom13.repositories.QuestionRepository;
import com.nhom13.services.QuestionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    private QuestionRepository questionRepo;
    
    @Override
    public List<Question> getQuestionBySurveyId(int surveyId) {
        return this.questionRepo.getQuestionBySurveyId(surveyId);
    }

    @Override
    public Question getQuestionById(int qId) {
        return this.questionRepo.getQuestionById(qId);
    }
    
}
