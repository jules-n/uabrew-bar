package com.julesn.uabrewbar.persistence.bar;

import com.julesn.uabrewbar.domain.Bar;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarRepository extends MongoRepository<Bar, String> {
}
