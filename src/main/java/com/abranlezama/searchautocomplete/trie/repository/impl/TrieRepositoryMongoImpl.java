package com.abranlezama.searchautocomplete.trie.repository.impl;

import com.abranlezama.searchautocomplete.trie.entity.QueryPrefix;
import com.abranlezama.searchautocomplete.trie.repository.TrieRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class TrieRepositoryMongoImpl implements TrieRepository {

    private final MongoTemplate triedbMongoTemplate;

    public TrieRepositoryMongoImpl(@Qualifier("triedbMongoTemplate") MongoTemplate triedbMongoTemplate) {
        this.triedbMongoTemplate = triedbMongoTemplate;
    }
    @Override
    public void upsertPrefixRecord(QueryPrefix queryPrefix) {
        Criteria criteria = Criteria.where("queryPrefix").is(queryPrefix.getQueryPrefix());


        Query query = new Query(criteria);

        Update update = new Update()
                .set("suggestions", queryPrefix.getSuggestions())
                .set("isEndOfQuery", queryPrefix.isEndOfQuery())
                .set("queryPrefix", queryPrefix.getQueryPrefix());

        triedbMongoTemplate.upsert(query, update, QueryPrefix.class);

    }

    @Override
    public QueryPrefix getPrefixSuggestions(String queryPrefix) {
        Criteria criteria = Criteria.where("queryPrefix").is(queryPrefix);

        Query query = new Query(criteria);

        return triedbMongoTemplate.findOne(query, QueryPrefix.class);
    }
}
