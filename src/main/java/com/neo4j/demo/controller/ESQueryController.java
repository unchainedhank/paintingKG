package com.neo4j.demo.controller;

import com.neo4j.demo.service.ElasticSearchService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class ESQueryController {
    @Resource
    private ElasticSearchService searchService;

    /**
     * 索引常量
     */
    private static final String PAINTER_INDEX = "painter_idx";//画家索引
    private static final String PAINTING_INDEX = "painting_idx";//画作索引


    /**
     * 导入所有画家的数据
     * @throws IOException 找不到service
     */
    @RequestMapping("/import/painters")
    public void importContent() throws IOException {
        searchService.importAllPainters();
    }

    @RequestMapping(value = "/search/{index}/{keyword}/{pageNo}/{pageSize}", method = RequestMethod.GET)
    public ArrayList<Map<String, Object>> searchPainterByName(@PathVariable String index,
                                                              @PathVariable String keyword,
                                                              @PathVariable int pageNo,
                                                              @PathVariable int pageSize) throws IOException {
        return searchService.search(index, keyword, pageNo, pageSize);

    }



}
