/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.repositories.impl;

import com.nhom13.pojo.Resident;
import com.nhom13.pojo.ResidentVisitor;
import com.nhom13.pojo.User;
import com.nhom13.pojo.Visitor;
import com.nhom13.repositories.VisitorRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ADMIN
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class VisitorRepositoryImpl implements VisitorRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Visitor> loadVisitors(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Visitor> query = b.createQuery(Visitor.class);
        Root r = query.from(Visitor.class);
        query.select(r);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(r.get("active"), Short.parseShort("1")));

        String status = params.get("status");
        if (status != null && !status.isEmpty()) {
            predicates.add(b.like(r.get("status"), status));
        }

        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(b.desc(r.get("id")));
        Query q = s.createQuery(query);
        return q.getResultList();
    }

    @Override
    public Visitor getVisitorById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Visitor.class, id);
    }

    @Override
    public void updateVisitor(Visitor v) {
        Session s = this.factory.getObject().getCurrentSession();
        Date currentDate = new Date();
        v.setUpdatedDate(currentDate);
        v.setActive(Short.parseShort("1"));
        s.update(v);
    }

    @Override
    public void deleteVisitor(int id) {
        Visitor v = this.getVisitorById(id);
        v.setActive(Short.parseShort("0"));
    }

    //lấy id resident từ id user
    public int getIdResident(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Resident> q = b.createQuery(Resident.class);
        
        Root rR = q.from(Resident.class);

        q.select(rR);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rR.get("userId"), id));

        q.where(predicates.toArray(Predicate[]::new));

        Query query = s.createQuery(q);
        
        Resident r = (Resident) query.getSingleResult();
        return r.getId();
    }

    @Override
    public List<Object[]> loadVisitorsByResidentId(int id) {
        int residentId = this.getIdResident(id);
        
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object> q = b.createQuery(Object.class);

        Root rV = q.from(Visitor.class);
        Root rR = q.from(Resident.class);
        Root rRV = q.from(ResidentVisitor.class);
        
        q.multiselect(rV.get("id"), rV.get("name"), rV.get("relation"), rV.get("createdDate"), rV.get("updatedDate"), rV.get("status"));

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rV.get("id"), rRV.get("visitorId")));
        predicates.add(b.equal(rR.get("id"), rRV.get("residentId")));
        predicates.add(b.equal(rR.get("id"), residentId));
        predicates.add(b.equal(rV.get("active"), Short.parseShort("1")));

        q.where(predicates.toArray(Predicate[]::new));
        q.orderBy(b.desc(rV.get("id")));

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public void createVisitor(Visitor v, int userId) {
        Session s = this.factory.getObject().getCurrentSession();
        
        Date currentDate = new Date();
        v.setCreatedDate(currentDate);
        v.setUpdatedDate(currentDate);
        v.setActive(Short.parseShort("1"));
        v.setStatus("waiting");
        s.save(v);
        
        Resident r = s.get(Resident.class, this.getIdResident(userId));
        ResidentVisitor rv = new ResidentVisitor();
        rv.setVisitorId(v);
        rv.setResidentId(r);
        s.save(rv);
    }

}
