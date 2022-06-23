package com.julesn.uabrewbar.persistence.bar;

import com.julesn.uabrewbar.domain.Bar;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Map;

public class BarRepositoryCustomImpl implements BarRepositoryCustom {

    @Setter(onMethod_ = {@Autowired})
    private MongoTemplate mongoTemplate;

    @Override
    public Bar getBarByCriteria(Map<String, Object> criteria) {
        Query query = new Query();
        criteria.entrySet().forEach(entry -> query.addCriteria(new Criteria(entry.getKey()).is(entry.getValue())));
        return mongoTemplate.findOne(query, Bar.class, Bar.collection);
    }

    @Override
    public boolean deleteBarByCriteria(Map<String, Object> criteria) {
        Query query = new Query();
        criteria.entrySet().forEach(entry -> query.addCriteria(new Criteria(entry.getKey()).is(entry.getValue())));
        return mongoTemplate.findAndRemove(query, Bar.class, Bar.collection) != null;
    }

    @Override
    public boolean update(Bar bar, String name) {
        return false;
    }
}
