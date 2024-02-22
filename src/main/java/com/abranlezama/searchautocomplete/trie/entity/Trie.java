package com.abranlezama.searchautocomplete.trie.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trie_db")
@Data
public class Trie {
    @Id
    private String id;
    private final TrieNode root;
    public Trie() {
        this.root = new TrieNode(" ");
    }

}
