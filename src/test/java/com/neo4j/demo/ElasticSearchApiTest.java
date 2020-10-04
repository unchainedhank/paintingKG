package com.neo4j.demo;

import com.neo4j.demo.entity.node.Painter;
import net.minidev.json.JSONObject;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

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

    @Test
    void getIndex() throws IOException {
        //1、构建 获取索引的请求
        GetIndexRequest request = new GetIndexRequest("xk_index");
        //2、客户端判断该索引是否存在
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        //3、打印
        System.out.println("该索引是否存在："+exists);
    }

    @Test
    void deleteIndex() throws IOException {
        //1、构建 删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest("xk_index");
        //2、客户段执行删除的请求
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        //3、打印
        System.out.println("是否删除成功："+response.isAcknowledged());
    }

    @Test
    void createDocument() throws IOException {
        Painter painter = new Painter();

        //1、构建请求
        IndexRequest request = new IndexRequest("user_index");

        //2、设置规则  PUT /user_index/user/_doc/1
        request.id("1");//设置id
        request.timeout(TimeValue.timeValueSeconds(1));//设置超时时间

        //3、将数据放入到请求中,以JSON的格式存放
        request.source(JSONObject.toJSONString((Map<String, ? extends Object>) painter), XContentType.JSON);

        //4、客户端发送请求,获取响应结果
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        //5、打印
        System.out.println("响应结果："+response.toString());
    }
}
