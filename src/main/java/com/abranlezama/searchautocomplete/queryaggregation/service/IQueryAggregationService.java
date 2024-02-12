package com.abranlezama.searchautocomplete.queryaggregation.service;

import com.abranlezama.searchautocomplete.queryaggregation.dto.QueryLogDTO;

public interface IQueryAggregationService {

    void aggregateQuery(QueryLogDTO queryLog);
}
