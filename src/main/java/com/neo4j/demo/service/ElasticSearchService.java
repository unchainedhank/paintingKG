package com.neo4j.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo4j.demo.entity.Painter;
import com.neo4j.demo.entity.Painting;
import com.neo4j.demo.repository.PainterRepository;
import com.neo4j.demo.repository.PaintingRepository;
import com.neo4j.demo.util.ElasticUtil;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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


    public String importContent() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        List<Painter> painters = painterRepo.findLimitPainters(76);
        List<Painting> paintings = paintingRepo.findLimitPaintings(430);



        for (int i = 0; i < painters.size(); i++) {
            bulkRequest.add(
                    new IndexRequest()
                    .source(new ObjectMapper().writeValueAsString(painters) + new ObjectMapper().writeValueAsString(paintings), XContentType.JSON));
        }

        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulk.hasFailures()? "fail" : "success";
    }

    public boolean indexExists() throws IOException {
        GetIndexRequest request = new GetIndexRequest(painterIndex);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    public void indexCreate() {

    }

}
