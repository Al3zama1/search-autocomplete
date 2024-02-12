package com.abranlezama.searchautocomplete.queryaggregation.service.impl;

import com.abranlezama.searchautocomplete.queryaggregation.dto.QueryLogDTO;
import com.abranlezama.searchautocomplete.queryaggregation.entity.QueryRecord;
import com.abranlezama.searchautocomplete.queryaggregation.entity.WeeklyEntry;
import com.abranlezama.searchautocomplete.queryaggregation.repository.QueryRecordRepository;
import com.abranlezama.searchautocomplete.queryaggregation.service.IQueryAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueryAggregationService implements IQueryAggregationService {

    private final QueryRecordRepository queryRecordRepository;

    @Override
    public void aggregateQuery(QueryLogDTO queryLog) {
        Optional<QueryRecord> queryRecordOptional = queryRecordRepository.findQueryRecordByQuery(queryLog.getQuery());
        LocalDate startOfWeekDate = queryLog.getTime().toLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        QueryRecord queryRecord;
        WeeklyEntry weeklyEntry;

        if (queryRecordOptional.isEmpty()) {
            queryRecord = new QueryRecord();
            queryRecord.setQuery(queryLog.getQuery());
            queryRecord.getWeeklyEntries().add(new WeeklyEntry(1, startOfWeekDate));
        } else {
            queryRecord = queryRecordOptional.get();
            weeklyEntry = queryRecord.getWeeklyEntries().getLast();

            if (startOfWeekDate.equals(weeklyEntry.getWeekOf())) {
                weeklyEntry.setQueryCount(weeklyEntry.getQueryCount() + 1);
            } else {
                queryRecord.getWeeklyEntries().add(new WeeklyEntry(1, startOfWeekDate));
            }
        }

        queryRecordRepository.save(queryRecord);
    }
}
