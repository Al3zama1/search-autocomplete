package com.abranlezama.searchautocomplete.trie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "trie_db")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryPrefix {
    @Id
    private String id;
    @Indexed(unique = true)
    private String queryPrefix;
    private boolean isEndOfQuery;
    private List<String> suggestions = new ArrayList<>();
}
