package edu.es.demo;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
public class EsTest {
    @Resource
    private RestHighLevelClient client;
    @Test
    void creatIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("ft_idx");
         CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);

    }


}
