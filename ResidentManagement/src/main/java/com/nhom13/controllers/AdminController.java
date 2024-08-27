/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.controllers;

import com.nhom13.pojo.User;
import com.nhom13.services.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ADMIN
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userSer;

    @GetMapping("/admin")
    public String addAdmin(Model model) {
        model.addAttribute("user", new User());
        return "admin";
    }

    @PostMapping("/admin")
    public String createUser(@ModelAttribute(value = "user") @Valid User u,
            BindingResult result) {

        if (!result.hasErrors()) {
            try {
                this.userSer.addAdmin(u);
                return "redirect:/";
            } catch (Exception ex) {
                System.err.println(ex.getMessage());

                return "redirect:/admin?duplicate";
            }
        }
        return "admin";

    }

}
