package com.julesn.uabrewbar.persistence.bar;

import com.julesn.uabrewbar.domain.Bar;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Map;

public class BarRepositoryCustomImpl implements BarRepositoryCustom {

    private MongoTemplate mongoTemplate;

    public BarRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

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
        var query = new Query();
        query.addCriteria(new Criteria("name").is(bar));
        Update update = new Update();
        update.set("name", bar.getName());
        update.set("country", bar.getCountry());
        update.set("places", bar.getPlaces());
        var result = mongoTemplate.updateFirst(query, update, Bar.class, Bar.collection);
        return result.wasAcknowledged();
    }
}
