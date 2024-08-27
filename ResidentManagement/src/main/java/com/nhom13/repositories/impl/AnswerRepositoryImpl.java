/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.repositories.impl;

import com.nhom13.pojo.Answer;
import com.nhom13.repositories.AnswerRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ADMIN
 */
@Repository
@Transactional
public class AnswerRepositoryImpl implements AnswerRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    public Answer check(Answer a) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery q = b.createQuery(Answer.class);
        Root r = q.from(Answer.class);
        q.select(r);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(r.get("questionId"), a.getQuestionId()));
        predicates.add(b.equal(r.get("residentId"), a.getResidentId()));

        q.where(predicates.toArray(Predicate[]::new));

        Query query = s.createQuery(q);
        try {
            Answer answer = (Answer) query.getSingleResult();
            return answer;
        } catch (NoResultException e) {
            return null; // Nếu không có kết quả nào phù hợp, trả về true
        }

    }

    @Override
    public void createAnswer(Answer a) {
        Session s = this.factory.getObject().getCurrentSession();
        Answer existingAnswer = check(a);
        if (existingAnswer == null) {
            s.save(a);
        } else {
            existingAnswer.setContent(a.getContent());
            existingAnswer.setCreatedDate(new Date());
            existingAnswer.setQuestionId(a.getQuestionId());
            existingAnswer.setResidentId(a.getResidentId());
            s.save(existingAnswer);
        }

    }

}
