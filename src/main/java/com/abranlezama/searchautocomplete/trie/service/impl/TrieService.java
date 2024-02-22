package com.abranlezama.searchautocomplete.trie.service.impl;

import com.abranlezama.searchautocomplete.queryaggregation.entity.QueryRecord;
import com.abranlezama.searchautocomplete.queryaggregation.repository.QueryRecordRepository;
import com.abranlezama.searchautocomplete.trie.entity.QueryPrefix;
import com.abranlezama.searchautocomplete.trie.entity.Trie;
import com.abranlezama.searchautocomplete.trie.entity.TrieNode;
import com.abranlezama.searchautocomplete.trie.entity.TrieNodeCache;
import com.abranlezama.searchautocomplete.trie.repository.TrieRepository;
import com.abranlezama.searchautocomplete.trie.service.ITrieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrieService implements ITrieService {

    private final QueryRecordRepository queryRecordRepository;
    private final TrieRepository trieRepository;


    @Override
    public void buildTrie() {
        Trie trie = new Trie();
        Page<QueryRecord> page;
        int pageNumber = 0;

        do {
            Pageable pageable = PageRequest.of(pageNumber++, 20);
            page = queryRecordRepository.findAll(pageable);
            List<QueryRecord> queryRecords = page.getContent();

            for(QueryRecord record : queryRecords) {
                buildTrie(record.getQuery(), 0, record.getTotalQueries(), trie.getRoot());
            }

        } while (page.hasNext());

        log.info(trie.toString());
    }

    private void buildTrie(String query, int index, int queryCount, TrieNode currentNode) {
        if (index >= query.length()) {
            currentNode.setFrequency(currentNode.getFrequency() + queryCount);
            currentNode.setEndOfQuery(true);
            return;
        }
        Map<String, TrieNode> children = currentNode.getChildren();
        String prefix = query.substring(0, index + 1);

        if (children.containsKey(prefix)) currentNode = children.get(prefix);
        else {
            currentNode = new TrieNode(prefix);
            children.put(prefix, currentNode);
        }

        index++;
        buildTrie(query, index, queryCount, currentNode);
        updateCache(currentNode, query, queryCount);
        saveSearchPrefix(currentNode, query, index);
    }

    private void saveSearchPrefix(TrieNode currentNode, String query, int index) {
        QueryPrefix queryPrefix = new QueryPrefix();
        List<String> suggestions = currentNode.getCache().stream().map(TrieNodeCache::getQuery).toList();

        queryPrefix.setQueryPrefix(query.substring(0, index));
        queryPrefix.setSuggestions(suggestions);
        queryPrefix.setEndOfQuery(currentNode.isEndOfQuery());
        trieRepository.upsertPrefixRecord(queryPrefix);
    }

    private void updateCache(TrieNode currentNode, String query, int queryCount) {
        if (currentNode.getCache().size() < 5) {
            currentNode.getCache().add(new TrieNodeCache(query, queryCount));
        } else {
            int min = Integer.MAX_VALUE;
            int minIndex = -1;

            for (int i = 0; i < 5; i++) {
                if (currentNode.getCache().get(i).getQueryCount() < min) {
                    min = currentNode.getCache().get(i).getQueryCount();
                    minIndex = i;
                }
            }

            if (queryCount > min) {
                currentNode.getCache().get(minIndex).setQuery(query);
                currentNode.getCache().get(minIndex).setQueryCount(queryCount);
            }

        }

    }
}
