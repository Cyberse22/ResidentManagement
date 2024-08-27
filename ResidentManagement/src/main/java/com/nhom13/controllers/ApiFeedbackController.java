/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.controllers;

import com.nhom13.DTOs.FeedbackDTO;
import com.nhom13.DTOs.FeedbackRequestDTO;
import com.nhom13.pojo.Feedback;
import com.nhom13.services.FeedbackService;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api/")
@CrossOrigin
public class ApiFeedbackController {

    @Autowired
    private FeedbackService feedbackSer;

    @DeleteMapping("/feedbacks/{feedbackId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void solve(@PathVariable(value = "feedbackId") int id) {
        this.feedbackSer.solveFeedback(id);
    }

    @DeleteMapping("/feedbacks/delete/{feedbackId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "feedbackId") int id) {
        this.feedbackSer.deleteFeedback(id);
    }
    
    

    @GetMapping(value = "/feedbacks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks(@RequestParam Map<String, String> params) {
        List<Object[]> feedbacks = this.feedbackSer.loadFeedbacks(params);
        List<FeedbackDTO> dtoList = new ArrayList<>();
        for (Object[] obj : feedbacks) {
            Integer id = (Integer) obj[0];
            String content = (String) obj[1];
            String firstName = (String) obj[2];
            String lastName = (String) obj[3];
            Short stShort = Short.parseShort(String.valueOf(obj[4]));
            Date createdDate = (Date) obj[5];

            FeedbackDTO dto = new FeedbackDTO(id, content, firstName, lastName, stShort, createdDate);
            dtoList.add(dto);
        }

        if (feedbacks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    //-------POST Feedback----------//
    @PostMapping("/feedbacks")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFeedback(@RequestBody FeedbackRequestDTO dto) {
        Feedback f = new Feedback();
        f.setContent(dto.getContent());
        this.feedbackSer.createFeedback(f);
    }
}
