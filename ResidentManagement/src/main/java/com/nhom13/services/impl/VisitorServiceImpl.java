/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.services.impl;

import com.nhom13.pojo.Visitor;
import com.nhom13.repositories.VisitorRepository;
import com.nhom13.services.VisitorService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class VisitorServiceImpl implements VisitorService{
    @Autowired
    private VisitorRepository visitorRepo;

    @Override
    public List<Visitor> loadVisitors(Map<String, String> params) {
        return this.visitorRepo.loadVisitors(params);
    }

    @Override
    public Visitor getVisitorById(int id) {
        return this.visitorRepo.getVisitorById(id);
    }

    @Override
    public void updateVisitor(Visitor v) {
        this.visitorRepo.updateVisitor(v);
    }

    @Override
    public void deleteVisitor(int id) {
        this.visitorRepo.deleteVisitor(id);
    }

    @Override
    public List<Object[]> loadVisitorsByResidentId(int id) {
        return this.visitorRepo.loadVisitorsByResidentId(id);
    }

    @Override
    public void createVisitor(Visitor v, int userId) {
        this.visitorRepo.createVisitor(v, userId);
    }
    
}
