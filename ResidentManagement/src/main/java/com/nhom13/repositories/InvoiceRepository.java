package com.nhom13.repositories;

import com.nhom13.pojo.Invoice;
import com.nhom13.pojo.Resident;

import java.util.List;
import java.util.Map;

public interface InvoiceRepository {
    void createOrUpdateInvoice (Invoice invoice);
    void deleteInvoice (int id);
    public Invoice getInvoiceById (int id);
    List<Invoice> getByResidentId(int residentId);
    List<Object[]> getInvoiceByUserId (int userId, Map<String,String> params);
    void updatePaymentProve (Invoice invoice);
   
    void createMultiple (List<Invoice> invoices);
    
}
