package com.nhom13.repositories;

import com.nhom13.pojo.ElectronicLocker;
import com.nhom13.pojo.Item;

import java.util.List;
import java.util.Map;

public interface ElectronicLockerRepository {
    List<Object[]> getAllElectronicLockers(Map<String, String> params);
    void addElectronicLocker(ElectronicLocker el);
    ElectronicLocker getElectronicLockerById(int id);
    void updateElectronicLocker(ElectronicLocker el);
    void deleteElectronicLocker(int id);
    ElectronicLocker getLockerByResidentId (int residentId);
}

