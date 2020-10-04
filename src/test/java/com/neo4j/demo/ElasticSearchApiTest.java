package com.neo4j.demo;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ElasticSearchApiTest {
    //@Qualifier表明注入的是哪个bean
    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;
}
