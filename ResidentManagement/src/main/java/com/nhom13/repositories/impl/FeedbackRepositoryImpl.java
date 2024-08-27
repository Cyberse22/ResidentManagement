/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.repositories.impl;

import com.nhom13.pojo.Feedback;
import com.nhom13.pojo.Resident;
import com.nhom13.pojo.User;
import com.nhom13.repositories.FeedbackRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ADMIN
 */
@Repository
@Transactional
public class FeedbackRepositoryImpl implements FeedbackRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    
      public long countFeedbacks(Map<String, String> params){
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);

        Root<Feedback> rF = q.from(Feedback.class);
        Root<Resident> rR = q.from(Resident.class);

        q.select(b.count(rF));
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rF.get("id"), rR.get("userId")));

        q.where(predicates.toArray(Predicate[]::new));
        return s.createQuery(q).getSingleResult();
    }
    
    @Override
    public List<Object[]> loadFeedbacks(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = b.createQuery(Object[].class);

        Root rU = query.from(User.class);
        Root rR = query.from(Resident.class);
        Root rF = query.from(Feedback.class);

        query.multiselect(rF.get("id"), rF.get("content"), rU.get("firstName"),
                rU.get("lastName"), rF.get("status"), rF.get("createdDate"));

        List<Predicate> predicates = new ArrayList<>();

        //join User and Resident on User.id == Resident.userId 
        predicates.add(b.equal(rU.get("id"), rR.get("userId")));
        //join Feedback and Resident on Feedback.residentId == Resident.id
        predicates.add(b.equal(rF.get("residentId"), rR.get("id")));

        String status = params.get("status");
        if (status != null && !status.isEmpty()) {
            predicates.add(b.equal(rF.get("status"), Short.parseShort(status)));
        }

        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(b.desc(rF.get("createdDate")));

        Query q = s.createQuery(query);
        
        
        String p = params.get("page");
        int page = p != null && !p.isEmpty() ? Integer.parseInt(p) : 1;
        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE").toString());

        long totalRecords = countFeedbacks(params);
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
    public Feedback getFeedbackById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Feedback f = s.get(Feedback.class, id);
        return f;
    }

    @Override
    public void solveFeedback(int id) {

        Feedback f = this.getFeedbackById(id);
        f.setStatus(Short.parseShort("1"));
    }

    @Override
    public void deleteFeedback(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Feedback f = this.getFeedbackById(id);
        s.delete(f);

    }
    
    
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
    public void createFeedback(Feedback f, int userId) {
        Session s = this.factory.getObject().getCurrentSession();
        Resident r = s.get(Resident.class, this.getIdResident(userId));

        Date currentDate = new Date();
        f.setCreatedDate(currentDate);
        f.setStatus(Short.parseShort("0"));
        f.setResidentId(r);
        s.save(f);
    }

}
