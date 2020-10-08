package com.neo4j.demo.controller;

import com.neo4j.demo.service.ElasticSearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class ESQueryController {
    @Resource
    private ElasticSearchService searchService;

    @RequestMapping("/import/painters")
    public void importContent() throws IOException {
        searchService.importAllPainters();
    }

}
