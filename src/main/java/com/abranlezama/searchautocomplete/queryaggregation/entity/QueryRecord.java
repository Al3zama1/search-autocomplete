package com.abranlezama.searchautocomplete.queryaggregation.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "aggregated_query_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryRecord {

    @Id
    private String id;
    private String query;
    private List<WeeklyEntry> weeklyEntries = new ArrayList<>();

}
