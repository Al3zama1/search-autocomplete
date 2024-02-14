package com.abranlezama.searchautocomplete.trie.service.impl;

import com.abranlezama.searchautocomplete.queryaggregation.entity.QueryRecord;
import com.abranlezama.searchautocomplete.queryaggregation.repository.QueryRecordRepository;
import com.abranlezama.searchautocomplete.trie.entity.Node;
import com.abranlezama.searchautocomplete.trie.entity.NodeCache;
import com.abranlezama.searchautocomplete.trie.entity.Trie;
import com.abranlezama.searchautocomplete.trie.service.ITrieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrieService implements ITrieService {

    private final QueryRecordRepository queryRecordRepository;



    @Override
    @Scheduled(fixedRate = 60000) // 60000 milliseconds = 1 minute
    public void buildTrie() {
        Trie trie = new Trie();
        List<QueryRecord> queryRecords = queryRecordRepository.findAll();

        for(QueryRecord record : queryRecords) {
            buildTrie(record.getQuery(), 0, record.getTotalQueries(), trie.getRoot());
        }
    }

    private void buildTrie(String query, int index, int queryCount, Node currentNode) {
        if (index >= query.length()) {
            currentNode.setFrequency(currentNode.getFrequency() + queryCount);
            currentNode.setEndOfQuery(true);
            return;
        }
        Map<String, Node> children = currentNode.getChildren();
        String prefix = query.substring(0, index + 1);

        if (children.containsKey(prefix)) currentNode = children.get(prefix);
        else {
            currentNode = new Node(prefix);
            children.put(prefix, currentNode);
        }

        index++;
        buildTrie(query, index, queryCount, currentNode);
        updateCache(currentNode, query, queryCount);
    }

    private void updateCache(Node currentNode, String query, int queryCount) {
        if (currentNode.getCacheCount() < 5) {
            currentNode.getCache()[currentNode.getCacheCount()] = new NodeCache(query, queryCount);
            currentNode.setCacheCount(currentNode.getCacheCount() + 1);
        } else {
            int min = Integer.MAX_VALUE;
            int minIndex = -1;

            for (int i = 0; i < 5; i++) {
                if (currentNode.getCache()[i].getQueryCount() < min) {
                    min = currentNode.getCache()[i].getQueryCount();
                    minIndex = i;
                }
            }

            if (queryCount > min) {
                currentNode.getCache()[minIndex].setQuery(query);
                currentNode.getCache()[minIndex].setQueryCount(queryCount);
            }

        }

    }
}
