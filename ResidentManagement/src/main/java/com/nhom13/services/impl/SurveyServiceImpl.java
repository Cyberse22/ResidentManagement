/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.services.impl;

import com.nhom13.pojo.Survey;
import com.nhom13.repositories.SurveyRepository;
import com.nhom13.services.SurveyService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class SurveyServiceImpl implements SurveyService{
    
    @Autowired
    private SurveyRepository surRepo;
    
    @Override
    public List<Survey> loadSurveys(Map<String, String> params) {
        return this.surRepo.loadSurveys(params);
    }

    @Override
    public List<Object[]> statSurveyById(int id) {
        return this.surRepo.statSurveyById(id);
    }

    @Override
    public void addSurvey(List<String> questions, Survey s) {
        this.surRepo.addSurvey(questions, s);
    }

    @Override
    public Survey getSurveyById(int id) {
       return this.surRepo.getSurveyById(id);
    }

    @Override
    public void blockSurvey(int id) {
        this.surRepo.blockSurvey(id);
    }

    @Override
    public List<Object[]> getSurveyByUserId(int userId) {
        return this.surRepo.getSurveyByUserId(userId);
    }

    @Override
    public void deleteSurvey(int id) {
        this.surRepo.deleteSurvey(id);
    }
    
}
