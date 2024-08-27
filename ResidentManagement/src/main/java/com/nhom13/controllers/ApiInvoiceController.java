/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.controllers;

import com.nhom13.DTOs.UserInvoiceDTO;
import com.nhom13.pojo.Invoice;
import com.nhom13.services.InvoiceService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api/")
@CrossOrigin
public class ApiInvoiceController {
    
    @Autowired
    private InvoiceService invoiceService;
    
    
    @GetMapping(path = "/user/{userId}/invoices/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserInvoiceDTO>> getInvoiceByUserId (@PathVariable(value = "userId") int userId,
            @RequestParam Map<String, String> params){
        List<Object[]> listObjs = this.invoiceService.getInvoiceByUserId(userId, params);
        List<UserInvoiceDTO> listInvoice = new ArrayList<>();
        
        for(Object[] obj : listObjs){
            int id = (int)obj[0];
            String name = (String)obj[1];
            long amount = (long)obj[2];
            Date dueDate = (Date)obj[3];
            String status = (String)obj[4];
            String paymentProve = (String)obj[5];
            Date createdDate = (Date)obj[6];
            
            UserInvoiceDTO ui = new UserInvoiceDTO(id, name, amount, dueDate, status, paymentProve, createdDate);
            listInvoice.add(ui);
        }
        
        return new ResponseEntity<>(listInvoice, HttpStatus.OK);
    }
    
    
    @PostMapping(path = "/invoices/{invoiceId}/", consumes = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.MULTIPART_FORM_DATA_VALUE
    })
    @ResponseStatus(HttpStatus.OK)
    public void updatePaymentProve ( @RequestPart MultipartFile[] file,
            @PathVariable(value = "invoiceId") int id){
        
        Invoice invoice = this.invoiceService.getInvoiceById(id);
        if (file.length > 0)
            invoice.setFile(file[0]);
        
        this.invoiceService.updatePaymentProve(invoice);
        
    }
    
}
