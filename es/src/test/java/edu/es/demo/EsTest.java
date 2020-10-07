package edu.es.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.es.demo.POJO.Painter;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class EsTest {
    @Resource
    private RestHighLevelClient client;

    /**
     * Painter:
     *   painter_index: "painter_idx"
     */
    final static String painter_index = "painter_idx";

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
        DeleteIndexRequest request = new DeleteIndexRequest(painter_index);
        AcknowledgedResponse response = client.indices().delete(request,RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    @Test
    void addDocument() throws IOException {
        Painter painter = new Painter((long) 123, "马远");
        IndexRequest request = new IndexRequest(painter_index);
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

    @Test
    void isExists() throws IOException {
        GetRequest request = new GetRequest(painter_index, "1");
        //不获取返回的上下文
        request.fetchSourceContext(new FetchSourceContext(false));

        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    void getDocument() throws IOException {
        GetRequest request = new GetRequest("painter_idx", "1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
        System.out.println(response);
    }

    @Test
    void updateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("painter_idx", "1");
        Painter painter = new Painter((long) 1234, "卡拉瓦乔");
        request.timeout("1s");
        request.doc(new ObjectMapper().writeValueAsString(painter),XContentType.JSON);

        UpdateResponse response = client.update(request,RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    @Test
    void deleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("painter_idx", "1");
        request.timeout("1s");
        DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //批量导入
    @Test
    void BulkRequest(ArrayList<Painter> painters) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
//        ArrayList<Painter> painters = new ArrayList<>();
        for (int i = 0; i < painters.size(); i++) {
            bulkRequest.add(
                    new IndexRequest("painter_idx")
//                    .id("" + (i+1))不加就是随机
                    .source(new ObjectMapper().writeValueAsString(painters.get(i)), XContentType.JSON)
            );
        }

        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures());
    }


    //搜索
    @Test
    void search() throws IOException {
        SearchRequest searchRequest = new SearchRequest(painter_index);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        //QueryBuilders.termQuery() 精确查找
        //QueryBuilders.matchAllQuery() 匹配所有
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("name", "卡拉瓦乔");
        searchBuilder.query(queryBuilder)
                     .timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(searchBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(new ObjectMapper().writeValueAsString(searchResponse.getHits()));

        for (SearchHit documentFields :
                searchResponse.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }

    }

}