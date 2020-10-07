package com.neo4j.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo4j.demo.entity.Painter;
import com.neo4j.demo.entity.Painting;
import com.neo4j.demo.repository.PainterRepository;
import com.neo4j.demo.repository.PaintingRepository;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Component
public class ElasticSearchService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Resource
    private PainterRepository painterRepo;
    @Resource
    private PaintingRepository paintingRepo;

    /**
     * 数据库常量
     */
    private static final String PAINTER_INDEX = "painter_idx";//画家
    private static final String PAINTING_INDEX = "painting_idx";//作品


    /**
     * 从neo4j批量导入数据
      * @return 是否成功
     * @throws IOException 有没有bulk
     */
    public String importContent() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();//批量处理类bulk
        bulkRequest.timeout("10s");

        //从neo4j拿出来
        List<Painter> painters = painterRepo.findLimitPainters(76);
        List<Painting> paintings = paintingRepo.findLimitPaintings(430);

        if (!(indexExists(PAINTER_INDEX) && indexExists(PAINTING_INDEX))) {
            indexCreate(PAINTER_INDEX);
            indexCreate(PAINTING_INDEX);
        }

        for (int i = 0; i < painters.size(); i++) {
            bulkRequest.add(
                    new IndexRequest()
                            .source(new ObjectMapper().writeValueAsString(painters) + new ObjectMapper().writeValueAsString(paintings), XContentType.JSON));
        }


        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulk.hasFailures()? "fail" : "success";
    }

    public boolean indexExists(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    public void indexCreate(String index) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(index);
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
    }

}
