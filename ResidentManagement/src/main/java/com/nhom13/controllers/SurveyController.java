/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhom13.pojo.Admin;
import com.nhom13.pojo.Resident;
import com.nhom13.pojo.Survey;
import com.nhom13.pojo.User;
import com.nhom13.services.NotificationService;
import com.nhom13.services.ResidentService;
import com.nhom13.services.SurveyService;
import com.nhom13.services.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ADMIN
 */
@Controller
public class SurveyController {

    @Autowired
    private SurveyService surService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notService;
    
    
    
    @RequestMapping("/surveys")
    public String surveys(Model model, @RequestParam() Map<String, String> params) {
        model.addAttribute("surveys", this.surService.loadSurveys(params));
        model.addAttribute("totalPages", params.get("totalPages"));
        model.addAttribute("currentPage", params.get("currentPage"));
        return "surveys";
    }

    @GetMapping("/survey_detail/{surveyId}")
    public String surveysDetail(@PathVariable(value = "surveyId") int id, Model model) {
        model.addAttribute("stats", this.surService.statSurveyById(id));
        return "surveyDetail";
    }

    @GetMapping("/add_survey")
    public String createSurveyView(Model model) {
        model.addAttribute("survey", new Survey());

        return "addSurvey";
    }

    @PostMapping("/add_survey")
    public String createSurvey(@ModelAttribute(value = "survey") @Valid Survey s,
            @RequestParam("contentsList") String contentsListJson,
            Principal principal, BindingResult result) throws JsonProcessingException {
        
        List<String> contentsList = Arrays.asList(new ObjectMapper().readValue(contentsListJson, String[].class));

        if (!result.hasErrors()) {
            try {
                Admin admin = this.userService.getAdminByUsername(principal.getName());
                s.setAdminId(admin);

                this.surService.addSurvey(contentsList, s);
                
                String title = "Thông báo";
                String body = "Một khảo sát mới vừa được tạo, cư dân vui lòng thực hiện";
                List<String> tokenList = new ArrayList<>();
                List<User> userList = this.userService.listAllUser();
                
                for(User u : userList){
                    if(u.getNotificationToken()!=null && !u.getNotificationToken().isEmpty()){
                        String token = u.getNotificationToken();
                        tokenList.add(token);
                    }
                }
                
                for(String t : tokenList){
                    this.notService.sendNotification(t, title, body);
                }
                
                
                
                return "redirect:/surveys";
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        
        return "redirect:/";
    }

}
