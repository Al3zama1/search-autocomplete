package com.abranlezama.searchautocomplete.trie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Node {
    private final String prefix;
    private NodeCache[] cache = new NodeCache[5];
    private int cacheCount = 0;
    private boolean isEndOfQuery;
    private int frequency = 0;
    private final Map<String, Node> children = new HashMap<>();


}
