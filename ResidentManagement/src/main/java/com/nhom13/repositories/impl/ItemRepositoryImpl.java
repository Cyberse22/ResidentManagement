package com.nhom13.repositories.impl;

import com.nhom13.pojo.Item;
import com.nhom13.repositories.ItemRepository;
import java.util.ArrayList;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.query.Query;

@Repository
@Transactional
public class ItemRepositoryImpl implements ItemRepository {
    @Autowired
    private LocalSessionFactoryBean factoryBean;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addItem(Map<String, Item> items) {
        Session s = Objects.requireNonNull(this.factoryBean.getObject()).getCurrentSession();
        try{
            for(Map.Entry<String, Item> entry : items.entrySet()){
                Item item = entry.getValue();
                s.save(item);
            }
            return true;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Item> getAllItemById(int id, Map<String, String> params)
    {
        Session s = factoryBean.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Item> q = b.createQuery(Item.class);
        Root r = q.from(Item.class);
        q.select(r);
        
        List<Predicate> predicates = new ArrayList<>();
        
        predicates.add(b.equal(r.get("electronicLockerId"), id));
        
        String status = params.get("status");
        if (status != null && !status.isEmpty()) {
            predicates.add(b.equal(r.get("status"), Short.parseShort(status)));
        }
        
        q.where(predicates.toArray(Predicate[]::new));
        q.orderBy(b.desc(r.get("id")));
        
        Query query = s.createQuery(q);
        return query.getResultList();
    }

    public Item getItemById(int itemId)
    {
        Session s = factoryBean.getObject().getCurrentSession();
        return s.get(Item.class, itemId);
    }

    public void updateOrCreateItem(Item item)
    {
        Session s = factoryBean.getObject().getCurrentSession();
        if (item.getId() != null)
            s.update(item);
        else s.save(item);
    }

    @Override
    public void deleteItem(int id) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        Item i = this.getItemById(id);
        s.delete(i);
    }
}
