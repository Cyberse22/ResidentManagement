/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.controllers;

import com.nhom13.pojo.ElectronicLocker;
import com.nhom13.pojo.Item;
import com.nhom13.pojo.Resident;
import com.nhom13.pojo.User;
import com.nhom13.services.ElectronicLockerService;
import com.nhom13.services.ItemService;
import com.nhom13.services.NotificationService;
import com.nhom13.services.ResidentService;
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
@RequestMapping(path = "/api/")
public class ApiItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ResidentService residentService;
    @Autowired
    private ElectronicLockerService lockerService;
    @Autowired
    private NotificationService notService;

    @DeleteMapping("/item/{itemId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable(value = "itemId") int id) {
        this.itemService.deleteItem(id);
    }

    //locker id => user id 
    @GetMapping(path = "/item/{lockerId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> getItems(@PathVariable(value = "lockerId") int id, @RequestParam Map<String, String> params) {

        Resident rObj = this.residentService.getResidentByUserId(id);

        ElectronicLocker lObj = this.lockerService.getLockerByResidentId(rObj.getId());
        List<Item> listItems = this.itemService.getAllItemById(lObj.getId(), params);

        //xuat thong bao
        return new ResponseEntity<>(listItems, HttpStatus.OK);
    }

}
