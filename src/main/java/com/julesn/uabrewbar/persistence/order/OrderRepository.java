package com.julesn.uabrewbar.persistence.order;

import com.julesn.uabrewbar.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>, OrderRepositoryCustom {
}
