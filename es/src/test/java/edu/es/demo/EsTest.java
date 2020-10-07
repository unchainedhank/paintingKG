package edu.es.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.es.demo.POJO.Painter;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
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

    @Test
    void addDocument() throws IOException {
        Painter painter = new Painter((long) 123, "马远");
        IndexRequest request = new IndexRequest("painter_idx");
        //设置请求
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        ObjectMapper mapper = new ObjectMapper();
        //把json放入请求
        request.source(mapper.writeValueAsString(painter), XContentType.JSON);
        //发送请求
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
    }


}
