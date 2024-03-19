package com.abranlezama.searchautocomplete.api.query.controller;

import com.abranlezama.searchautocomplete.api.query.service.IQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
public class QueryController {

    private final IQueryService queryService;
    private final StringRedisTemplate stringRedisTemplate;

    @GetMapping("/search")
    public void query(@RequestParam String query) {
        log.info("The query is " + query);
    }

    @GetMapping("/suggest")
    public List<String> suggest(@RequestParam String queryPrefix) {
        String key = String.format("query:prefix:%s", queryPrefix);
        List<String> querySuggestions = stringRedisTemplate.opsForList().range(key, 0, -1);

        if (querySuggestions != null && !querySuggestions.isEmpty()) return querySuggestions;
        querySuggestions = queryService.queryPrefixSuggestions(queryPrefix);

        if (querySuggestions.isEmpty()) return querySuggestions;
        stringRedisTemplate.opsForList().rightPushAll(key, querySuggestions);
        stringRedisTemplate.expire(key, Duration.ofMinutes(10));
        return querySuggestions;
    }
}
