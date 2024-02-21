package com.abranlezama.searchautocomplete.queryaggregation.repository.impl;

import com.abranlezama.searchautocomplete.queryaggregation.entity.QueryRecord;
import com.abranlezama.searchautocomplete.queryaggregation.entity.WeeklyEntry;
import com.abranlezama.searchautocomplete.queryaggregation.repository.QueryAggregationRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
//@RequiredArgsConstructor
public class QueryAggregationMongoImpl implements QueryAggregationRepository {

    private final MongoTemplate querydbMongoTemplate;

    public QueryAggregationMongoImpl(@Qualifier("querydbMongoTemplate") MongoTemplate querydbMongoTemplate) {
        this.querydbMongoTemplate = querydbMongoTemplate;
    }

    @Override
    public QueryRecord updateExistingQueryWeeklyEntry(String query, LocalDate weekOf) {
        Criteria criteria = Criteria.where("query").is(query)
                .and("weeklyEntries.weekOf").is(weekOf);

        Update update = new Update().inc("totalQueries", 1)
                .inc("weeklyEntries.$.queryCount", 1);

        Query dbQuery = new Query(criteria);
        dbQuery.fields().include("query", "totalQueries");

        return querydbMongoTemplate.findAndModify(
                dbQuery,
                update,
                FindAndModifyOptions.options().returnNew(true),
                QueryRecord.class
        );
    }

    @Override
    public Optional<QueryRecord> findQueryRecordByQuery(String query) {
        Criteria criteria = Criteria.where("query").is(query);

        Query dbQuery = new Query(criteria);
        dbQuery.fields().include("query");


        return Optional.ofNullable(querydbMongoTemplate.findOne(dbQuery, QueryRecord.class));
    }

    @Override
    public UpdateResult insertWeeklyEntry(String query, WeeklyEntry weeklyEntry) {
        Criteria criteria = Criteria.where("query").is(query);

        Update update = new Update().push("weeklyEntries", weeklyEntry)
                .inc("totalQueries", 1);

        Query dbQuery = new Query(criteria);

        return querydbMongoTemplate.updateFirst(
                dbQuery,
                update,
                QueryRecord.class
        );
    }

    @Override
    public QueryRecord saveQueryRecord(QueryRecord queryRecord) {
        return querydbMongoTemplate.insert(queryRecord);
    }
}
