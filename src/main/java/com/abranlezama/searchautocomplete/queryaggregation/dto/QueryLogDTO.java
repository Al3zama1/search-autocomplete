package com.abranlezama.searchautocomplete.queryaggregation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryLogDTO {
    private String query;
    private Instant time;
}
