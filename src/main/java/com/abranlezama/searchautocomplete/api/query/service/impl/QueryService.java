package com.abranlezama.searchautocomplete.api.query.service.impl;

import com.abranlezama.searchautocomplete.api.query.service.IQueryService;
import com.abranlezama.searchautocomplete.trie.entity.QueryPrefix;
import com.abranlezama.searchautocomplete.trie.repository.TrieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryService implements IQueryService {

    private final TrieRepository trieRepository;

    @Override
    public List<String> queryPrefixSuggestions(String queryPrefix) {
        QueryPrefix querySuggestions = trieRepository.getPrefixSuggestions(queryPrefix);

        if (querySuggestions == null) return List.of();
        return querySuggestions.getSuggestions();
    }
}
