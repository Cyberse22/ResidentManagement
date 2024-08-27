package com.nhom13.controllers;

import com.nhom13.pojo.Invoice;
import com.nhom13.pojo.Resident;
import com.nhom13.services.InvoiceService;
import com.nhom13.services.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ResidentService residentService;

    @GetMapping("/invoice-residents")
    public String invoiceResidentsView(Model model, @RequestParam Map<String, String> params) {
        
        
        List<Object[]> residents = residentService.getResidentWithInvoices(params);
        model.addAttribute("residents", residents);
        model.addAttribute("totalPages", params.get("totalPages"));
        model.addAttribute("currentPage", params.get("currentPage"));
        
        
        return "invoice-residents";
    }

    @GetMapping("/invoice-residents/{id}/all")
    public String getInvoiceOfUser(@PathVariable int id, @RequestParam Map<String, String> params, Model model) {
        model.addAttribute("residentId", id);
        model.addAttribute("invoices", invoiceService.getByResidentId(id));
        return "invoice-resident-detail";
    }

    @GetMapping("/invoice-residents/{residentId}/{invoiceId}")
    public String getInvoice(@PathVariable("invoiceId") int id, Model model) {
        model.addAttribute("invoice", invoiceService.getInvoiceById(id));
        return "invoice-detail";
    }

    @GetMapping("/invoice-residents/{residentId}/create")
    public String getInvoiceCreateView(@PathVariable int residentId, Model model) {
        Invoice invoice = new Invoice();
        invoice.setResidentId(new Resident(residentId));
        invoice.setStatus("unpaid");
        invoice.setCreatedDate(new Date());
        model.addAttribute("invoice", invoice);
        return "invoice-detail";
    }

    @PostMapping("/invoices")
    public String createOrUpdate(@ModelAttribute Invoice invoice) {
        int residentId = invoice.getResidentId().getId();
        
        Resident r = this.residentService.getResidentById(residentId);
        long amount = invoice.getAmount();
        if (amount > 0) {
            
            invoice.setResidentId(r);
            invoiceService.createOrUpdateInvoice(invoice);
            return "redirect:/invoice-residents/" + residentId + "/all";
        }
        return "redirect:/invoice-residents/" + residentId + "/all?error";
    }

    @DeleteMapping("/invoice-residents/{residentId}/invoices/{invoiceId}")
    public String deleteInvoice(@PathVariable int residentId, @PathVariable int invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return "redirect:/invoice-residents/" + residentId + "/all";
    }
    
    @GetMapping("/invoices/create-multiple")
    public String createMultiple(Model model) {
        List<Resident> residents = this.residentService.getAll();
        
        model.addAttribute("residents", residents);
        model.addAttribute("invoice", new Invoice());
        
        
        return "invoice-create-multiple";
    }
    
    @PostMapping("/invoices/create-multiple")
    public String postCreateMultiple(@ModelAttribute Invoice invoice,
                                    @RequestParam(name= "residents", required = false) List<Integer> residentIds) {
        
        
        long amount = invoice.getAmount();
        if(amount > 0 ){
            invoiceService.createMultiple(invoice, residentIds);
            return "redirect:/invoice-residents";
        }
        
        return "redirect:/invoices/create-multiple?error";
        
        
        
    }

}
