package edu.es.demo;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
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

    @Test
    void ExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("ft_idx");
        boolean isExist = client.indices().exists(request,RequestOptions.DEFAULT);
        System.out.println(isExist);
    }

    @Test
    void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("ft_idx");
        AcknowledgedResponse response = client.indices().delete(request,RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }



}
