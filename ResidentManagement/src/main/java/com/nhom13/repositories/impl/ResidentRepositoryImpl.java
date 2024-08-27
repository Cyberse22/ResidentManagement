/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.repositories.impl;

import com.nhom13.pojo.ElectronicLocker;
import com.nhom13.pojo.Invoice;
import com.nhom13.pojo.Resident;
import com.nhom13.pojo.User;
import com.nhom13.repositories.ResidentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.Query;
import javax.persistence.criteria.*;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ADMIN
 */
@Repository
@Transactional
public class ResidentRepositoryImpl implements ResidentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    public long countResidents(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> query = b.createQuery(Long.class);

        Root<Resident> rR = query.from(Resident.class);
        query.select(b.count(rR));

        List<Predicate> predicates = new ArrayList<>();
        String block = params.get("block");
        if (block != null && !block.isEmpty()) {
            predicates.add(b.equal(rR.get("userId").get("active"), 0));
        } else {
            predicates.add(b.equal(rR.get("userId").get("active"), 1));
        }
        query.where(predicates.toArray(new Predicate[predicates.size()]));

        return s.createQuery(query).getSingleResult();
    }

    @Override
    public List<Resident> loadResident(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = b.createQuery(Object[].class);

        Root rR = query.from(Resident.class);
        Root rU = query.from(User.class);

        query.multiselect(rR.get("id"), rU.get("firstName"), rU.get("lastName"),
                rU.get("dob"), rU.get("address"), rU.get("phone"), rU.get("active"), rU.get("id"));

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(rU.get("id"), rR.get("userId")));

        String block = params.get("block");
        if (block != null && !block.isEmpty()) {
            predicates.add(b.equal(rU.get("active"), 0));
        } else {
            predicates.add(b.equal(rU.get("active"), 1));
        }

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            predicates.add(b.like(rU.get("lastName"), String.format("%%%s%%", kw)));
        }

        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(b.asc(rR.get("id")));

        Query q = s.createQuery(query);

        String p = params.get("page");
        int page = p != null && !p.isEmpty() ? Integer.parseInt(p) : 1;
        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE").toString());

        long totalRecords = countResidents(params);
        long totalPages = (totalRecords + pageSize - 1) / pageSize;

        if (page > totalPages) {
            page = (int) totalPages;
        }

        int start = (page - 1) * pageSize;
        q.setFirstResult(start);
        q.setMaxResults(pageSize);

        params.put("totalPages", String.valueOf(totalPages));
        params.put("currentPage", String.valueOf(page));

        return q.getResultList();
    }

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        User u = s.get(User.class, id);
        return u;
    }

    @Override
    public void deleteUser(int id) {
        User u = this.getUserById(id);
        u.setActive(Short.parseShort("0"));
    }

    @Override
    public Resident getResidentByUserId(int userId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Resident> q = b.createQuery(Resident.class);
        Root r = q.from(Resident.class);
        q.select(r);

        q.where(b.equal(r.get("userId"), userId));

        Query query = s.createQuery(q);
        return (Resident) query.getSingleResult();
    }

    @Override
    public Resident getResidentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Resident r = s.get(Resident.class, id);
        return r;
    }

    @Override
    public List<Object[]> getResidentWithInvoices(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery query = b.createQuery(Object.class);

        Root rR = query.from(Resident.class);
        Root rI = query.from(Invoice.class);
        Root rU = query.from(User.class);

        query.multiselect(rR.get("id"), rU.get("firstName"), rU.get("lastName"),
                b.sum(b.<Integer>selectCase().when(b.equal(rI.get("status"), "paid"), 1).otherwise(0)),
                b.sum(b.<Integer>selectCase().when(b.equal(rI.get("status"), "waiting"), 1).otherwise(0)),
                b.sum(b.<Integer>selectCase().when(b.equal(rI.get("status"), "unpaid"), 1).otherwise(0)));

        List<Predicate> predicates = new ArrayList<>();

        //join resident - invoice 
        predicates.add(b.equal(rR.get("id"), rI.get("residentId")));
        //join resident - user
        predicates.add(b.equal(rR.get("userId"), rU.get("id")));
        
        //get user active = 1
        predicates.add(b.equal(rU.get("active"), Short.parseShort("1")));
        //group by
        query.groupBy(rR.get("id"), rU.get("firstName"), rU.get("lastName"));
        
        query.where(predicates.toArray(Predicate[]::new));

        Query q = s.createQuery(query);


        String p = params.get("page");

        int page = p != null && !p.isEmpty() ? Integer.parseInt(p) : 1;
        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE").toString());

        long totalRecords = countResidents(params);
        long totalPages = Math.round((float) totalRecords / pageSize);

        if (page > totalPages) {
            page = (int) totalPages;
        }

        q.setMaxResults(pageSize);
        q.setFirstResult((page - 1) * pageSize);

        List<Object[]> residents = q.getResultList();

        params.put("totalPages", String.valueOf(totalPages));
        params.put("currentPage", String.valueOf(page));

        return residents;

    }

    @Override
    public List<Resident> getAll() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Resident> q = b.createQuery(Resident.class);
        Root r = q.from(Resident.class);
        q.select(r);

        q.where(b.equal(r.get("userId").get("active"), Short.parseShort("1")));

        Query query = s.createQuery(q);

        return query.getResultList();

    }
}
