package com.abranlezama.searchautocomplete.trie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrieNodeCache {
    private String query;
    private int queryCount;
}
