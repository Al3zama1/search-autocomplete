package com.abranlezama.searchautocomplete.queryaggregation.repository;

import com.abranlezama.searchautocomplete.queryaggregation.entity.QueryRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface QueryRecordRepository extends MongoRepository<QueryRecord, String> {


    @Query(value = "{ 'query':  ?0 }", fields = "{ 'query':  1}")
    Optional<QueryRecord> findByQuery(String query);

    @Query(value = "{ $and: [ { 'query': ?0 }, { 'weeklyEntries.weekOf': ?1 } ] }",
            fields = "{ 'query': 1, 'totalQueries': 1, 'weeklyEntries.$': 1 }")
    Optional<QueryRecord> findByQueryAndDate(String query, LocalDate weekOf);

    @Transactional
    @Query(value = "{ 'query': ?0, 'weeklyEntries.weekOf': ?1 }",
            fields = "{ '_id': 1, 'weeklyEntries.$': 1 }")
    Optional<QueryRecord> updateQueryLog(String query, LocalDate weekOf);

}
