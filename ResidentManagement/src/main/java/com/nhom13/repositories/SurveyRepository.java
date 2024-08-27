/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.repositories;

import com.nhom13.pojo.Survey;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public interface SurveyRepository {
    List<Survey> loadSurveys(Map<String, String> params);
    List<Object[]> statSurveyById (int id);
    void addSurvey (List<String> questions, Survey s);
    Survey getSurveyById (int id);
    void blockSurvey (int id);
    List<Object[]> getSurveyByUserId (int userId);
    void deleteSurvey (int id);
}
