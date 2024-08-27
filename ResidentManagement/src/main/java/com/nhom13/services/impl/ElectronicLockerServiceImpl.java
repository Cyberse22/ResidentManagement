package com.nhom13.services.impl;

import com.nhom13.pojo.ElectronicLocker;
import com.nhom13.repositories.ElectronicLockerRepository;
import com.nhom13.services.ElectronicLockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ElectronicLockerServiceImpl implements ElectronicLockerService {
    @Autowired
    private ElectronicLockerRepository electronicLockerRepository;

    @Override
    public ElectronicLocker getElectronicLockerById(int id) {
        return electronicLockerRepository.getElectronicLockerById(id);
    }

    @Override
    public void updateElectronicLocker(ElectronicLocker el) {
        electronicLockerRepository.updateElectronicLocker(el);
    }

    @Override
    public void deleteElectronicLocker(int id) {
        electronicLockerRepository.deleteElectronicLocker(id);
    }

    @Override
    public void addElectronicLocker(ElectronicLocker el) {
        electronicLockerRepository.addElectronicLocker(el);
    }

    @Override
    public List<Object[]> getAllElectronicLockers(Map<String, String> params) {
        return electronicLockerRepository.getAllElectronicLockers(params);
    }

    @Override
    public ElectronicLocker getLockerByResidentId(int residentId) {
        return this.electronicLockerRepository.getLockerByResidentId(residentId);
    }
}
