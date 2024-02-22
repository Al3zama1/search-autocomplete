package com.abranlezama.searchautocomplete.trie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TrieNode {
    private final String prefix;
    private List<TrieNodeCache> cache = new ArrayList<>();
    private boolean isEndOfQuery;
    private int frequency = 0;
    private final Map<String, TrieNode> children = new HashMap<>();


}
