package com.abranlezama.searchautocomplete.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.abranlezama.searchautocomplete.trie.repository", mongoTemplateRef = "triedbMongoTemplate")
public class TrieDBConfiguration {
}
