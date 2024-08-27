/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.repositories.impl;

import com.nhom13.pojo.Admin;
import com.nhom13.pojo.ElectronicLocker;
import com.nhom13.pojo.Invoice;
import com.nhom13.pojo.Resident;
import com.nhom13.pojo.User;
import com.nhom13.repositories.ResidentRepository;
import com.nhom13.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ADMIN
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ResidentRepository residentRepository;

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> query = b.createQuery(User.class);
        Root r = query.from(User.class);
        query.select(r);

        query.where(b.equal(r.get("username"), username));

        Query q = s.createQuery(query);
        return (User) q.getSingleResult();
    }

    //Add resident by User and create a electronic locker for this resident 
    @Override
    public void addResident(User u) {
        Session s = this.factory.getObject().getCurrentSession();

        if (u.getId() != null) {
            s.update(u);
        } else {
            u.setRole("resident");
            u.setActive(Short.parseShort("1"));
            s.save(u);

            Resident r = new Resident();
            r.setBalance(Long.parseLong("0"));
            r.setUserId(u);
            s.save(r);

            ElectronicLocker l = new ElectronicLocker();
            l.setResidentId(r);
            s.save(l);
            
            Invoice i = new Invoice();
            i.setAmount(10000000);
            i.setActive(Short.parseShort("1"));
            i.setCreatedDate(new Date());
            i.setDueDate(new Date());
            i.setName("Phí đặt cọc");
            i.setStatus("paid");
            i.setResidentId(r);
            s.save(i);
        }

    }

    @Override
    public void addAdmin(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        u.setRole("admin");
        u.setActive(Short.parseShort("1"));
        s.save(u);

        Admin a = new Admin();
        a.setUserId(u);
        s.save(a);
    }

    @Override
    public Admin getAdminByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object> q = b.createQuery(Object.class);
        Root rU = q.from(User.class);
        Root rA = q.from(Admin.class);

        q.select(rA);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(rA.get("userId"), rU.get("id")));
        predicates.add(b.equal(rU.get("username"), username));

        q.where(predicates.toArray(Predicate[]::new));

        Query query = s.createQuery(q);
        return (Admin) query.getSingleResult();
    }

    @Override
    public boolean authUser(String username, String password) {
        User u = this.getUserByUsername(username);

        return this.passwordEncoder.matches(password, u.getPassword());
    }

    @Override
    public void updateUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        User user = s.get(User.class, u.getId());
        user.setPassword(u.getPassword());
        user.setAvatar(u.getAvatar());
        s.save(user);
    }

    @Override
    public void updateToken(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        User user = s.get(User.class, u.getId());
        user.setNotificationToken(u.getNotificationToken());
        s.save(user);
    }

    @Override
    public List<User> listAllUser() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root r = q.from(User.class);
        q.select(r);

        q.where(b.equal(r.get("active"), Short.parseShort("1")));

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public void deleteUser(int userId) {
        Session s = this.factory.getObject().getCurrentSession();
        Resident r = this.residentRepository.getResidentById(userId);
        User u = r.getUserId();
        s.delete(u);
    }
}
