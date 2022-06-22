package com.julesn.uabrewbar.persistence.sequence;

import com.julesn.uabrewbar.domain.Sequence;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class SequenceRepositoryCustomImpl implements SequenceRepositoryCustom {
    private MongoTemplate mongoTemplate;

    public SequenceRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean incrementCount() {
        var update = new Update();
        update.inc("count");
        var result = mongoTemplate.updateFirst(new Query(),update, Sequence.COLLECTION_NAME);
        return result.wasAcknowledged();
    }
}
