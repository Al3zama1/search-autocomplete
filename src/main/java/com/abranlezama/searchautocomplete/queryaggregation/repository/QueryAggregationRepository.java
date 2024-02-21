package com.abranlezama.searchautocomplete.queryaggregation.repository;

import com.abranlezama.searchautocomplete.queryaggregation.entity.QueryRecord;
import com.abranlezama.searchautocomplete.queryaggregation.entity.WeeklyEntry;
import com.mongodb.client.result.UpdateResult;

import java.time.LocalDate;
import java.util.Optional;

public interface QueryAggregationRepository {

    QueryRecord updateExistingQueryWeeklyEntry(String query, LocalDate weekOf);

    Optional<QueryRecord> findQueryRecordByQuery(String query);

    UpdateResult insertWeeklyEntry(String query, WeeklyEntry weeklyEntry);

    QueryRecord saveQueryRecord(QueryRecord queryRecord);
}
