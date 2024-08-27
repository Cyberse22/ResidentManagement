package com.nhom13.controllers;

import com.nhom13.services.ElectronicLockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/electronic-lockers")
public class ElectronicLockerController {
    @Autowired
    private ElectronicLockerService electronicLockerService;

    @GetMapping
    public String getAll(@RequestParam Map<String, String> params, Model model) {
        model.addAttribute("electronicLockers", this.electronicLockerService.getAllElectronicLockers(params));
        model.addAttribute("totalPages", params.get("totalPages"));
        model.addAttribute("currentPage", params.get("currentPage"));
        return "electronic-lockers";
    }
}
