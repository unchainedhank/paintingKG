package com.neo4j.demo;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ElasticSearchApiTest {
    //@Qualifier表明注入的是哪个bean
    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;

    /**
     * 创建索引
     */
    @Test
    void createIndex() throws IOException {
        //创建索引
        CreateIndexRequest request = new CreateIndexRequest("ft_idx");
        //客户端执行请求，获得响应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

        System.out.println(response.index());

    }
}
