/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.repositories.impl;

import com.nhom13.pojo.Admin;
import com.nhom13.pojo.Answer;
import com.nhom13.pojo.Question;
import com.nhom13.pojo.Resident;
import com.nhom13.pojo.Survey;
import com.nhom13.pojo.User;
import com.nhom13.repositories.SurveyRepository;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ADMIN
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class SurveyRepositoryImpl implements SurveyRepository{
    
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;
    
    public long countSurveys(Map<String, String> params){
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);

        Root<Survey> rS = q.from(Survey.class);

        q.select(b.count(rS));
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rS.get("adminId").get("id"), rS.get("adminId")));

        q.where(predicates.toArray(Predicate[]::new));
        return s.createQuery(q).getSingleResult();
    }

    @Override
    public List<Survey> loadSurveys(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Survey> query = b.createQuery(Survey.class);
        Root r = query.from(Survey.class);
        query.select(r);
        
        List<Predicate> predicates = new ArrayList<>();
        String name = params.get("name");
        if(name != null && !name.isEmpty()){
            predicates.add(b.like(r.get("title"), String.format("%%%s%%", name)));
        }
        
        String status = params.get("status");
        if (status!=null && !status.isEmpty()){
            predicates.add(b.equal(r.get("active"), Short.parseShort(status)));
        }
        
        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(b.desc(r.get("createdDate")));
        
        Query q = s.createQuery(query);
        
        String p = params.get("page");
        int page = p != null && !p.isEmpty() ? Integer.parseInt(p) : 1;
        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE").toString());

        long totalRecords = countSurveys(params);
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
    public List<Object[]> statSurveyById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object> query = b.createQuery(Object.class);
        
        Root rS = query.from(Survey.class);
        Root rQ = query.from(Question.class);
        Root rA = query.from(Answer.class);
        

        query.multiselect(rS.get("title"), rQ.get("content"),
                b.sum(b.<Integer>selectCase().when(b.equal(rA.get("content"), 1), 1).otherwise(0)),//content = 1 la good else bad
                b.sum(b.<Integer>selectCase().when(b.equal(rA.get("content"), 0), 1).otherwise(0)),
                rQ.get("id"));

        List<Predicate> predicates = new ArrayList<>();
        
        //join Survey and Question
        predicates.add(b.equal(rS.get("id"), rQ.get("surveyId")));
        //join Question and Answer
        predicates.add(b.equal(rQ.get("id"), rA.get("questionId")));
        
        predicates.add(b.equal(rS.get("id"), id)); //loc id survey 
        
        query.where(predicates.toArray(Predicate[]::new));//where
        
        //group by
        query.groupBy( 
                rQ.get("id"),
                rS.get("title"),
                rQ.get("content")
        );
        

        Query q = s.createQuery(query);
        return q.getResultList();
    }

    @Override
    public void addSurvey(List<String> questions, Survey su) {
        Session s = this.factory.getObject().getCurrentSession();
        
        su.setCreatedDate(new Date());
        su.setActive(Short.parseShort("1"));
        s.save(su);
        
        for(String content: questions){
            Question q = new Question();
            q.setContent(content);
            q.setSurveyId(su);
            q.setCreatedDate(new Date());
            s.save(q);
        }
    }

    @Override
    public Survey getSurveyById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Survey survey = s.get(Survey.class, id);
        return survey;
    }

    @Override
    public void blockSurvey(int id) {
        Survey s = this.getSurveyById(id);
        s.setActive(Short.parseShort("0"));
    }

    @Override
    public List<Object[]> getSurveyByUserId(int userId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object> q = b.createQuery(Object.class);
        
        Root rR = q.from(Resident.class);
        Root rA = q.from(Answer.class);
        Root rQ = q.from(Question.class);
        Root rS = q.from(Survey.class);
        
        q.multiselect(rS.get("id"),rS.get("title"));
        
        List<Predicate> predicates = new ArrayList<>();
        
        //join Question - Answer 
        predicates.add(b.equal(rQ.get("id"), rA.get("questionId")));
        //join Answer - Resident 
        predicates.add(b.equal(rA.get("residentId"), rR.get("id")));
        //join Question - Survey
        predicates.add(b.equal(rQ.get("surveyId"), rS.get("id")));
        
        //rR.userId = userId
        predicates.add(b.equal(rR.get("userId"), userId));
        
        //rS.active = 1
        predicates.add(b.equal(rS.get("active"), Short.parseShort("1")));
        
        q.where(predicates.toArray(Predicate[]::new));
        q.groupBy(rS.get("id"), rS.get("title"));
        
        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public void deleteSurvey(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Survey survey = this.getSurveyById(id);
        s.delete(survey);
    }

    
    
}
