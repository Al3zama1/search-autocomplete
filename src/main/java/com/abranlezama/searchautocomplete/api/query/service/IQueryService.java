package com.abranlezama.searchautocomplete.api.query.service;

import java.util.List;

public interface IQueryService {

    List<String> queryPrefixSuggestions(String queryPrefix);
}
