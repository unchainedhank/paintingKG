package com.neo4j.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo4j.demo.entity.Painter;
import com.neo4j.demo.entity.Painting;
import com.neo4j.demo.repository.PainterRepository;
import com.neo4j.demo.repository.PaintingRepository;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ElasticSearchService {
    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Resource
    private PainterRepository painterRepo;
    @Resource
    private PaintingRepository paintingRepo;


    /**
     * 数据库常量
     */
    private static final String PAINTER_INDEX = "painter_idx";//画家索引
    private static final String PAINTING_INDEX = "painting_idx";//作品索引


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

    /**
     * 从neo4j导入painters
     * @return 成功success
     * @throws IOException 找不到index
     */
    public String importAllPainters() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();//批量处理类bulk
        bulkRequest.timeout("10s");

        //从neo4j拿出来
//    l       List<Painter> painters = painterRepo.findLimitPainters(76);
        List<Painter> painters = painterRepo.findLimitPainters(76);

        if (!indexExists(PAINTER_INDEX)) {
            indexCreate(PAINTER_INDEX);
        }

        ObjectMapper mapper = new ObjectMapper();

        for (Painter painter : painters) {
                bulkRequest.add(
                    new IndexRequest(PAINTER_INDEX)
                            .source(mapper.writeValueAsString(painter), XContentType.JSON));
        }


        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulk.hasFailures()? "fail" : "success";

    }

    /**
     * 从neo4j导入paintings
     * @return 成功success
     * @throws IOException 找不到index
     */
    public String importAllPaintings() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();//批量处理类bulk
        bulkRequest.timeout("10s");

        //从neo4j拿出来
//    l       List<Painter> painters = painterRepo.findLimitPainters(76);
        List<Painting> paintings = paintingRepo.findLimitPaintings(326);

        if (!indexExists(PAINTING_INDEX)) {
            indexCreate(PAINTING_INDEX);
        }

        ObjectMapper mapper = new ObjectMapper();

        for (Painting painting : paintings) {
            bulkRequest.add(
                    new IndexRequest(PAINTING_INDEX)
                            .source(mapper.writeValueAsString(painting), XContentType.JSON));
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



    public ArrayList<Map<String, Object>> search(String index, String keyword, int pageNo, int pageSize) throws IOException {

        SearchRequest searchRequest = new SearchRequest(index);

        //QueryBuilders.termQuery() 精确查找
        //QueryBuilders.matchAllQuery() 匹配所有
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("name", keyword);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        searchRequest.source(sourceBuilder.query(queryBuilder)
                                            .timeout(new TimeValue(60, TimeUnit.SECONDS)));

        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        ArrayList<Map<String, Object>> entities = new ArrayList<>();



        for (SearchHit documentFields :
                searchResponse.getHits()) {
            entities.add(documentFields.getSourceAsMap());
        }
        return entities;

    }

}
