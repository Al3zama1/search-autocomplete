package com.abranlezama.searchautocomplete.queryaggregation.repository;

import com.abranlezama.searchautocomplete.queryaggregation.entity.QueryRecord;
import com.abranlezama.searchautocomplete.queryaggregation.entity.WeeklyEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QueryRecordRepository extends MongoRepository<QueryRecord, String> {

    Optional<QueryRecord> findQueryRecordByQuery(String query);

    @Query(value = "{ 'query':  ?0 }", fields = "{ 'weeklyEntries': { $slice:  -1 } }")
    Optional<QueryRecord> findLastWeeklyRecordByQuery(String query);

    @Query(value = "{ '_id' : ?0 }", fields = "{ 'weeklyEntries': 1 }")
    QueryRecord addWeeklyRecord(String id, WeeklyEntry weeklyEntry);
}
