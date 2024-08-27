/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.services;



import com.nhom13.pojo.Resident;
import com.nhom13.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public interface ResidentService {
    List<Resident> loadResident (Map<String, String> params);
    User getUserById (int id);  //lấy user từ userId
    void deleteUser(int id);    //deactive user
    Resident getResidentByUserId (int userId);
    Resident getResidentById (int id);
    List<Object[]> getResidentWithInvoices(Map<String, String> params);
    List<Resident> getAll();
}
