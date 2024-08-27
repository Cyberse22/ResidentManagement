/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.controllers;

import com.nhom13.pojo.Resident;
import com.nhom13.pojo.User;
import com.nhom13.services.ResidentService;
import com.nhom13.services.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ADMIN
 */
@Controller
public class ResidentController {

    @Autowired
    private ResidentService resSer;
    @Autowired
    private UserService userSer;

    @GetMapping("/resident")
    public String createUserView(Model model) {
        model.addAttribute("user", new User());
        return "resident";
    }

    @PostMapping("/resident")
    public String createUser(@ModelAttribute(value = "user") @Valid User u,
            BindingResult result) {

        if (!result.hasErrors()) {
            try {
                this.userSer.addResident(u);
                return "redirect:/";
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                return "redirect:/resident?duplicate";
            }
        }
        return "resident";

    }


    @GetMapping("/resident/{userId}/")
    public String updateUser(Model model, @PathVariable(value = "userId") int userId) {

        Resident r = this.resSer.getResidentById(userId);
        User u = r.getUserId();
        u.setPassword("");
        u.setAvatar("");
        model.addAttribute("user", u);

        return "resident";
    }

}
