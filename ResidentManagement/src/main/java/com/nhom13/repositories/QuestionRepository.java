/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.repositories;

import com.nhom13.pojo.Question;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface QuestionRepository {
    List<Question> getQuestionBySurveyId (int surveyId);
    Question getQuestionById (int qId);
}
