package com.neo4j.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo4j.demo.entity.Painter;
import com.neo4j.demo.repository.PainterRepository;
import com.neo4j.demo.repository.PaintingRepository;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ElasticSearchService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Resource
    private PainterRepository painterRepo;
    @Resource
    private PaintingRepository paintingRepo;
    public boolean importContent(String keyWords) {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        List<Painter> painters = (List<Painter>) repository.findAll();

        for (int i = 0; i < painters.size(); i++) {
            bulkRequest.add(
                    new IndexRequest()
            )
        }

    }

}
