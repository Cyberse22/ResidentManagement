/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.controllers;

import com.nhom13.services.NotificationService;
import com.nhom13.services.ResidentService;
import com.nhom13.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class ApiResidentController {
    @Autowired
    private ResidentService resSer;
    @Autowired
    private UserService userSer;
    
    
    @DeleteMapping("/resident/{residentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "residentId") int id) {
        this.resSer.deleteUser(id);        
    }
    
    @DeleteMapping("/resident/delete/{residentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePermanent(@PathVariable(value = "residentId") int id) {
        this.userSer.deleteUser(id);
    }
}
