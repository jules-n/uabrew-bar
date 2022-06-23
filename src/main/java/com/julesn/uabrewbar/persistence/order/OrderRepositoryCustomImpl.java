package com.julesn.uabrewbar.persistence.order;

import com.julesn.uabrewbar.domain.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Map;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private MongoTemplate mongoTemplate;

    public OrderRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean updateByCriteria(Map<String, Object> criteria, Map<String, Object> data) {
        Query query = new Query();
        criteria.entrySet().forEach(entry -> query.addCriteria(new Criteria(entry.getKey()).is(entry.getValue())));
        Update update = new Update();
        data.entrySet().forEach(entry -> update.set(entry.getKey(), entry.getValue()));
        return mongoTemplate.updateFirst(query, update, Order.class, Order.COLLECTION).wasAcknowledged();
    }

    @Override
    public Order getByCriteria(Map<String, Object> criteria) {
        Query query = new Query();
        criteria.entrySet().forEach(entry -> query.addCriteria(new Criteria(entry.getKey()).is(entry.getValue())));
        return mongoTemplate.findOne(query,Order.class, Order.COLLECTION);
    }

    @Override
    public List<Order> getListByCriteria(Map<String, Object> criteria) {
        Query query = new Query();
        criteria.entrySet().forEach(entry -> query.addCriteria(new Criteria(entry.getKey()).is(entry.getValue())));
        return mongoTemplate.find(query, Order.class, Order.COLLECTION);
    }
}
