package com.nhom13.services;

import com.nhom13.pojo.ElectronicLocker;

import java.util.List;
import java.util.Map;

public interface ElectronicLockerService {
    ElectronicLocker getElectronicLockerById(int id);
    void updateElectronicLocker(ElectronicLocker el);
    void deleteElectronicLocker(int id);
    void addElectronicLocker(ElectronicLocker el);
    List<Object[]> getAllElectronicLockers(Map<String, String> params);
    ElectronicLocker getLockerByResidentId (int residentId);
}
