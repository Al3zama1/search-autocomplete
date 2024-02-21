package com.abranlezama.searchautocomplete.queryaggregation.service.impl;

import com.abranlezama.searchautocomplete.queryaggregation.dto.QueryLogDTO;
import com.abranlezama.searchautocomplete.queryaggregation.entity.QueryRecord;
import com.abranlezama.searchautocomplete.queryaggregation.entity.WeeklyEntry;
import com.abranlezama.searchautocomplete.queryaggregation.repository.QueryAggregationRepository;
import com.abranlezama.searchautocomplete.queryaggregation.service.IQueryAggregationService;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryAggregationService implements IQueryAggregationService {

    private final QueryAggregationRepository queryAggregationRepository;

    @Override
    public void aggregateQuery(QueryLogDTO queryLog) {
        LocalDate startOfWeekDate = queryLog.getTime().atZone(ZoneId.of("America/Los_Angeles")).toLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        Optional<QueryRecord> queryRecordOptional = queryAggregationRepository.findQueryRecordByQuery(queryLog.getQuery());

        if (queryRecordOptional.isPresent()) {
            // attempt to
            QueryRecord aggregatedLog = queryAggregationRepository.updateExistingQueryWeeklyEntry(queryLog.getQuery(), startOfWeekDate);

            if (aggregatedLog == null) {
                UpdateResult updateResult = queryAggregationRepository.insertWeeklyEntry(queryLog.getQuery(), new WeeklyEntry(1, startOfWeekDate));
                log.info(updateResult.toString());

            }
        } else {
            QueryRecord queryRecord = new QueryRecord();
            queryRecord.setQuery(queryLog.getQuery());
            queryRecord.setTotalQueries(1);
            queryRecord.getWeeklyEntries().add(new WeeklyEntry(1, startOfWeekDate));
            queryAggregationRepository.saveQueryRecord(queryRecord);
        }

    }
}
