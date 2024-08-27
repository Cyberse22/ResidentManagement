package com.nhom13.repositories.impl;

import com.nhom13.pojo.Invoice;
import com.nhom13.pojo.Resident;
import com.nhom13.pojo.Survey;
import com.nhom13.pojo.User;
import com.nhom13.repositories.InvoiceRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.core.env.Environment;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class InvoiceRepositoryImpl implements InvoiceRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Autowired
    private Environment env;
    
    public long countInvoices(Map<String, String> params){
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);

        Root<Invoice> rI = q.from(Invoice.class);
        Root<Resident> rR = q.from(Resident.class);

        q.select(b.count(rI));
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rR.get("id"), rI.get("residentId")));

        q.where(predicates.toArray(Predicate[]::new));
        return s.createQuery(q).getSingleResult();
    }

    

    @Override
    public Invoice getInvoiceById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Invoice.class, id);
    }

    //Unpaid vừa tạo chưa thanh toán
    //Waiting chờ xác nhận từ admin
    //Paid admin đã xác nhận
    @Override
    public void createOrUpdateInvoice(Invoice invoice) {
        Session s = this.factory.getObject().getCurrentSession();

        if (invoice.getId() == null) {
            invoice.setActive(Short.parseShort("1"));
            invoice.setCreatedDate(new Date());
            invoice.setStatus("unpaid");
            s.save(invoice);
        } else {
            s.update(invoice);
        }

    }

    @Override
    public void deleteInvoice(int id) {
        Invoice i = this.getInvoiceById(id);
        i.setActive(Short.parseShort("0"));
    }

    public List<Invoice> getByResidentId(int residentId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Invoice> query = b.createQuery(Invoice.class);
        Root<Invoice> root = query.from(Invoice.class);

        Predicate condition = b.equal(root.get("residentId").get("id"), residentId);
        query.where(condition);

        query.orderBy(b.asc(root.get("id")));

        Query q = s.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Object[]> getInvoiceByUserId(int userId, Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object> q = b.createQuery(Object.class);

        Root rI = q.from(Invoice.class);
        Root rR = q.from(Resident.class);

        q.multiselect(rI.get("id"), rI.get("name"), rI.get("amount"), rI.get("dueDate"), rI.get("status"),
                 rI.get("paymentProve"), rI.get("createdDate"));

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(rI.get("residentId"), rR.get("id")));

        predicates.add(b.equal(rI.get("active"), Short.parseShort("1")));
        predicates.add(b.equal(rR.get("userId"), userId));
        
        String status = params.get("status");
        if (status != null && !status.isEmpty()){
            predicates.add(b.like(rI.get("status"), status));
        }
        
        q.where(predicates.toArray(Predicate[]::new));
        
        q.orderBy(b.desc(rI.get("id")));
        
        Query query = s.createQuery(q);
        return query.getResultList();

    }

    @Override
    public void updatePaymentProve(Invoice invoice) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(invoice);
    }
    
    
    

    @Override
    public void createMultiple(List<Invoice> invoices) {
        Session s = factory.getObject().getCurrentSession();
        invoices.forEach(i -> s.save(i));
    }
}
