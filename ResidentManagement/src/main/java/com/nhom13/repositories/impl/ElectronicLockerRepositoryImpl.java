package com.nhom13.repositories.impl;

import com.nhom13.pojo.ElectronicLocker;
import com.nhom13.pojo.Item;
import com.nhom13.pojo.Resident;
import com.nhom13.pojo.User;
import com.nhom13.repositories.ElectronicLockerRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Query;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class ElectronicLockerRepositoryImpl implements ElectronicLockerRepository {
    @Autowired
    private LocalSessionFactoryBean factoryBean;
    @Autowired
    private Environment env;
    
     public long countElectronicLockers(Map<String, String> params) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);

        Root<ElectronicLocker> rE = q.from(ElectronicLocker.class);
        Root<Resident> rR = q.from(Resident.class);
        

        q.select(b.count(rE));
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rR.get("id"), rE.get("residentId")));
        predicates.add(b.equal(rR.get("userId").get("active"), Short.parseShort("1")));

        q.where(predicates.toArray(Predicate[]::new));
        return s.createQuery(q).getSingleResult();
    }
     

    @Override
    public List<Object[]> getAllElectronicLockers(Map<String, String> params) {
        Session s = Objects.requireNonNull(this.factoryBean.getObject()).getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object> q = b.createQuery(Object.class);
        
        Root<ElectronicLocker> rE = q.from(ElectronicLocker.class);
        Root<User> rU = q.from(User.class);
        Root<Resident> rR = q.from(Resident.class);
        
        q.multiselect(rE.get("id"), rU.get("lastName"), rU.get("firstName"), rU.get("dob"), rU.get("address"), rU.get("phone"));

        List<Predicate> predicates = new ArrayList<>();
        
       //join 3 bang lai
       predicates.add(b.equal(rU.get("id"), rR.get("userId")));
       predicates.add(b.equal(rE.get("residentId"), rR.get("id")));
       
       //lay user active = 1
       predicates.add(b.equal(rU.get("active"), Short.parseShort("1")));
       
        String residentName = params.get("residentName");
        if (residentName != null && residentName.isEmpty()) {
            predicates.add(b.like(rU.get("lastName"), String.format("%%%s%%", residentName)));
        }
        
        q.where(predicates.toArray(Predicate[]::new));

        Query query = s.createQuery(q);
        
        
        String p = params.get("page");
        int page = p != null && !p.isEmpty() ? Integer.parseInt(p) : 1;
        int pageSize = Integer.parseInt(env.getProperty("PAGE_SIZE").toString());

        long totalRecords = countElectronicLockers(params);
        long totalPages = (totalRecords + pageSize - 1) / pageSize;

        if (page > totalPages) {
            page = (int) totalPages;
        }

        int start = (page - 1) * pageSize;
        query.setFirstResult(start);
        query.setMaxResults(pageSize);

        // Pass pagination information to params map
        params.put("totalPages", String.valueOf(totalPages));
        params.put("currentPage", String.valueOf(page));

        return query.getResultList();
    }

    @Override
    public void addElectronicLocker(ElectronicLocker el) {
        Session s = Objects.requireNonNull(this.factoryBean.getObject()).getCurrentSession();
        s.save(el);
    }

    @Override
    public ElectronicLocker getElectronicLockerById(int id) {
        Session s = Objects.requireNonNull(this.factoryBean.getObject()).getCurrentSession();
        return s.get(ElectronicLocker.class, id);
    }

    @Override
    public void updateElectronicLocker(ElectronicLocker el) {
        Session s = Objects.requireNonNull(this.factoryBean.getObject()).getCurrentSession();
        s.update(el);
    }

    @Override
    public void deleteElectronicLocker(int id) {
        Session s = Objects.requireNonNull(this.factoryBean.getObject()).getCurrentSession();
        ElectronicLocker el = s.load(ElectronicLocker.class, id);
        if (el != null) {
            s.delete(el);
        }
    }

    @Override
    public ElectronicLocker getLockerByResidentId(int residentId) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<ElectronicLocker> q = b.createQuery(ElectronicLocker.class);
        Root r = q.from(ElectronicLocker.class);
        q.select(r);
        q.where(b.equal(r.get("residentId"), residentId));
        Query query = s.createQuery(q);
        return (ElectronicLocker)query.getSingleResult();
    }


}
