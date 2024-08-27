/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.controllers;

import com.nhom13.DTOs.VisitorDTO;
import com.nhom13.pojo.Visitor;
import com.nhom13.services.VisitorService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@CrossOrigin
@RequestMapping("/api/")
public class ApiVisitorController {

    @Autowired
    private VisitorService visitorService;

    @DeleteMapping("/visitor/{visitorId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVisitor(@PathVariable(value = "visitorId") int id) {
        this.visitorService.deleteVisitor(id);
    }

    @GetMapping(path = "/visitor/{userId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VisitorDTO>> getVisitorByResidentId(@PathVariable(value = "userId") int id) {

        List<Object[]> listVisitors = this.visitorService.loadVisitorsByResidentId(id);
        List<VisitorDTO> listDTO = new ArrayList<>();

        for (Object[] obj : listVisitors) {
            int idx = (Integer) obj[0];
            String name = (String) obj[1];
            String relation = (String) obj[2];
            Date createdDate = (Date) obj[3];
            Date updatedDate = (Date) obj[4];
            String status = (String) obj[5];
            

            VisitorDTO dto = new VisitorDTO(id, name, relation, createdDate, updatedDate, status, idx);
            listDTO.add(dto);
        }

        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }
    
    @PostMapping("/visitor/")
    @ResponseStatus(HttpStatus.OK)
    public void create (@RequestParam Map<String, String> params){
        String name = params.get("name");
        String relation = params.get("relation");
        int userId = Integer.parseInt(params.get("userId"));
        Visitor v = new Visitor();
        v.setName(name);
        v.setRelation(relation);
        
        this.visitorService.createVisitor(v, userId);
    }

}
