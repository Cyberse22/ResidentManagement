/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.controllers;

import com.nhom13.services.ElectronicLockerService;
import com.nhom13.services.ResidentService;
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
public class IndexController {

    @Autowired
    private ResidentService resSer;
    @Autowired
    private ElectronicLockerService lockerSer;

    @RequestMapping("/")
    public String index(Model model, @RequestParam() Map<String, String> params) {
        model.addAttribute("residents", this.resSer.loadResident(params));
        model.addAttribute("totalPages", params.get("totalPages"));
        model.addAttribute("currentPage", params.get("currentPage"));
        
        return "index";
    }
}
