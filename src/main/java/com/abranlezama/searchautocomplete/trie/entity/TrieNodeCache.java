package com.abranlezama.searchautocomplete.trie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeCache {
    private String query;
    private int queryCount;
}
