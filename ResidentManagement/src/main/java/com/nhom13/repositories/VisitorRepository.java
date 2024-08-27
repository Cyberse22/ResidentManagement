/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.repositories;

import com.nhom13.pojo.Visitor;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public interface VisitorRepository {
    List<Visitor> loadVisitors (Map<String, String> params);
    Visitor getVisitorById (int id);
    void updateVisitor (Visitor v);
    void deleteVisitor (int id);
    List<Object[]> loadVisitorsByResidentId(int id);
    void createVisitor (Visitor v, int userId);
}
