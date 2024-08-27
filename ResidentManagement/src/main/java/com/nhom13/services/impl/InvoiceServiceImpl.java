package com.nhom13.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nhom13.pojo.Invoice;
import com.nhom13.pojo.Resident;
import com.nhom13.repositories.InvoiceRepository;
import com.nhom13.services.InvoiceService;
import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private Cloudinary cloudinary;



    @Override
    public void createOrUpdateInvoice(Invoice invoice)
    { 
        this.invoiceRepository.createOrUpdateInvoice(invoice); 
    }

    @Override
    public void deleteInvoice(int id) {
        this.invoiceRepository.deleteInvoice(id);
    }

    @Override
    public Invoice getInvoiceById(int id) {
        return this.invoiceRepository.getInvoiceById(id);
    }

    @Override
    public List<Invoice> getByResidentId(int residentId) {
        return invoiceRepository.getByResidentId(residentId);
    }

    @Override
    public List<Object[]> getInvoiceByUserId(int userId, Map<String, String> params) {
        return this.invoiceRepository.getInvoiceByUserId(userId, params);
    }

    @Override
    public void updatePaymentProve(Invoice invoice) {
        if(!invoice.getFile().isEmpty()){
            try {
                Map res = this.cloudinary.uploader().upload(invoice.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                invoice.setPaymentProve(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        invoice.setStatus("waiting");
        this.invoiceRepository.updatePaymentProve(invoice);
    }

    @Override
    public void createMultiple(Invoice invoice, List<Integer> residentIds) {
        List<Invoice> invoices = residentIds.stream().map(r -> {
            Invoice i = new Invoice();
            i.setName(invoice.getName());
            i.setAmount(invoice.getAmount());
            i.setDueDate(invoice.getDueDate());
            i.setCreatedDate(new Date());
            i.setResidentId(new Resident(r));
            i.setStatus("unpaid");
            i.setActive(Short.parseShort("1"));
            return i;
        }).collect(Collectors.toList());
        invoiceRepository.createMultiple(invoices);
    }
}
