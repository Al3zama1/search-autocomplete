package com.abranlezama.searchautocomplete.queryaggregation.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "query_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes({
        @CompoundIndex(useGeneratedName = true, unique = true, def = "{'query': 1, 'weeklyEntries.weekOf': 1}")
})
public class QueryRecord {

    @Id
    private String id;
    @Indexed(unique = true, useGeneratedName = true)
    private String query;
    private int totalQueries;
    private List<WeeklyEntry> weeklyEntries = new ArrayList<>();

}
