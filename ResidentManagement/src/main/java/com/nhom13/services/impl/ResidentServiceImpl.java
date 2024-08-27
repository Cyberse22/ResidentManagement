/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.services.impl;

import com.nhom13.pojo.Resident;
import com.nhom13.pojo.User;
import com.nhom13.repositories.ResidentRepository;
import com.nhom13.services.ResidentService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class ResidentServiceImpl implements ResidentService{
    
    @Autowired
    private ResidentRepository resRepo;
  

    @Override
    public List<Resident> loadResident(Map<String, String> params) {
        return this.resRepo.loadResident(params);
    }

    

    @Override
    public User getUserById(int id) {
        return this.resRepo.getUserById(id);
    }

    @Override
    public void deleteUser(int id) {
        this.resRepo.deleteUser(id);
    }

    @Override
    public Resident getResidentByUserId(int userId) {
        return this.resRepo.getResidentByUserId(userId);
    }

    @Override
    public Resident getResidentById(int id) {
        return this.resRepo.getResidentById(id);
    }

    @Override
    public List<Object[]> getResidentWithInvoices(Map<String, String> params) {
        return this.resRepo.getResidentWithInvoices(params);
    }

    @Override
    public List<Resident> getAll() {
        return this.resRepo.getAll();
    }

}
