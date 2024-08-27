/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.DTOs;

import com.nhom13.pojo.Question;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class SurveyDTO {
    private String title; 
    private List<questionDTO> questions;
    
    public SurveyDTO (String title, List<questionDTO> questions){
        this.title = title;
        this.questions = questions;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the questions
     */
    public List<questionDTO> getQuestions() {
        return questions;
    }

    /**
     * @param questions the questions to set
     */
    public void setQuestions(List<questionDTO> questions) {
        this.questions = questions;
    }
}
