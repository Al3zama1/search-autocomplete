package com.abranlezama.searchautocomplete.query.controller;

import com.abranlezama.searchautocomplete.query.service.IQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
public class QueryController {

    private final IQueryService queryService;

    @GetMapping("/search")
    public void query(@RequestParam String query) {
        log.info("The query is " + query);
    }

    @GetMapping("/suggest")
    public List<String> suggest(@RequestParam String queryPrefix) {
        return queryService.queryPrefixSuggestions(queryPrefix);
    }
}
