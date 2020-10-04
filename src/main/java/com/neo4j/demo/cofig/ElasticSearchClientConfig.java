package com.neo4j.demo.cofig;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchClientConfig {
    /**
     * 配置RestHighLevelClient对象，交给spring容器管理
     * @return RestHighLevelClient
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        //可以传递多个（数组)
                        new HttpHost("127.0.0.1", 9200, "http")
                )
        );
    }
}
