package com.abranlezama.searchautocomplete.query.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class QueryController {

    @GetMapping("/search")
    public void query(@RequestParam String query) {
        log.info("The query is " + query);
    }
}
