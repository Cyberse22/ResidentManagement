/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.controllers;

import com.nhom13.services.FeedbackService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ADMIN
 */
@Controller
public class FeedbackController {
    
    @Autowired
    private FeedbackService feedbackSer;
    
    @RequestMapping("/feedbacks")
    public String feedback (@RequestParam() Map<String, String> params, Model model){
        model.addAttribute("feedbacks",this.feedbackSer.loadFeedbacks(params));
        model.addAttribute("totalPages", params.get("totalPages"));
        model.addAttribute("currentPage", params.get("currentPage"));
        return "feedbacks";
    }
    
}
