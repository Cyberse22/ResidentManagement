/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.controllers;

import com.nhom13.DTOs.AnswerDTO;
import com.nhom13.DTOs.AnswerListDTO;
import com.nhom13.DTOs.SurveyDTO;
import com.nhom13.DTOs.UserSurveyDTO;
import com.nhom13.DTOs.questionDTO;
import com.nhom13.pojo.Answer;
import com.nhom13.pojo.Question;
import com.nhom13.pojo.Resident;
import com.nhom13.pojo.Survey;
import com.nhom13.services.AnswerService;
import com.nhom13.services.QuestionService;
import com.nhom13.services.ResidentService;
import com.nhom13.services.SurveyService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api/")
@CrossOrigin
public class ApiSurveyController {
    @Autowired
    private SurveyService surService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private ResidentService residentService;
    
    
    @DeleteMapping("/surveys/{surveyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void block (@PathVariable(value = "surveyId") int id){
        this.surService.blockSurvey(id);
    }
    
    @DeleteMapping("/surveys/delete/{surveyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable(value = "surveyId") int id){
        this.surService.deleteSurvey(id);
    }
    
    @GetMapping("/surveys/")
    public ResponseEntity<List<Survey>> loadSurveys (){
        Map<String, String> params = new HashMap<>();
        params.put("status", "1");
        
        List<Survey> surveys = this.surService.loadSurveys(params);
        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }
    
    @GetMapping("/surveys/{surveyId}/")
    public ResponseEntity<SurveyDTO> getSurveyById (@PathVariable(value = "surveyId") int id){
        
        List<questionDTO> listQuestions = new ArrayList<>();
        
        Survey survey = this.surService.getSurveyById(id);
        List<Question> questions = this.questionService.getQuestionBySurveyId(id);
        
        for(Question q : questions){
            questionDTO qDTO = new questionDTO(q.getId(), q.getContent(), q.getCreatedDate());
            
            listQuestions.add(qDTO);
        }
        
        SurveyDTO s = new SurveyDTO(survey.getTitle(),listQuestions);
        
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
    
    @PostMapping(path = "/surveys/{surveyId}/", consumes = {
        MediaType.APPLICATION_JSON_VALUE,
    })
    @ResponseStatus(HttpStatus.OK)
    public void submitAnswers (@RequestBody AnswerListDTO answerList){
        
        
        for(AnswerDTO aDTO : answerList.getListAnswer()){
            int content = Integer.parseInt(aDTO.getContent());
            
            Question q = this.questionService.getQuestionById(aDTO.getQuestionId());
            Resident r = this.residentService.getResidentByUserId(aDTO.getUserId());
            
            Answer a = new Answer();
            a.setContent(content);
            a.setQuestionId(q);
            a.setResidentId(r);
            a.setCreatedDate(new Date());
            this.answerService.createAnswer(a);
        }
    }
    
    
    @GetMapping(path = "/user/{userId}/surveys/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserSurveyDTO>> getSurveyByUserId (@PathVariable(value = "userId") int userId){
        List<Object[]> listSurveys = this.surService.getSurveyByUserId(userId);
        List<UserSurveyDTO> listDTO = new ArrayList<>();
        
        for(Object[] obj : listSurveys){
            int surveyId = (int)obj[0];
            String title = (String)obj[1];
            
            UserSurveyDTO us = new UserSurveyDTO(surveyId, title);
            listDTO.add(us);
        }
        
        
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }
    
}
