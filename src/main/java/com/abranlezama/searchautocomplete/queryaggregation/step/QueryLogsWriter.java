package com.abranlezama.searchautocomplete.queryaggregation.step;

import com.abranlezama.searchautocomplete.queryaggregation.dto.QueryLogDTO;
import com.abranlezama.searchautocomplete.queryaggregation.service.IQueryAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryLogsWriter implements ItemWriter<QueryLogDTO> {

    private final IQueryAggregationService queryAggregationService;

    @Override
    public void write(Chunk<? extends QueryLogDTO> chunk) throws Exception {

        for (QueryLogDTO queryLog : chunk) queryAggregationService.aggregateQuery(queryLog);
    }
}
