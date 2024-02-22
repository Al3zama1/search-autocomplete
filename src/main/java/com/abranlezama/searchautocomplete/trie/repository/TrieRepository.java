package com.abranlezama.searchautocomplete.trie.repository;

import com.abranlezama.searchautocomplete.trie.entity.QueryPrefix;

public interface TrieRepository {

    void upsertPrefixRecord(QueryPrefix queryPrefix);
}
