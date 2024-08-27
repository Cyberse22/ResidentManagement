/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.services.impl;

import com.nhom13.pojo.Answer;
import com.nhom13.repositories.AnswerRepository;
import com.nhom13.services.AnswerService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class AnswerServiceImpl implements AnswerService{
    
    @Autowired
    private AnswerRepository answerRepo;

    @Override
    public void createAnswer(Answer a) {
        this.answerRepo.createAnswer(a);
    }
    
}
