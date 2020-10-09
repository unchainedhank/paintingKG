package com.neo4j.demo.controller;

import com.neo4j.demo.service.ElasticSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@Api(value = "搜索controller", tags = "搜索框接口")
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
//    @RequestMapping("/import/painters")
//    public void importContent() throws IOException {
//        searchService.importAllPainters();
//    }

    @ApiOperation(value = "搜索候选词")
    @RequestMapping(value = "/search/{index}/{keyword}/{pageNo}/{pageSize}", method = RequestMethod.GET)
    public ArrayList<Map<String, Object>> searchPainterByName(@ApiParam(name = "index", value = "查询的库：painter_idx或painting_idx")
                                                              @PathVariable String index,
                                                              @ApiParam(name = "keyword", value = "查询的关键字")
                                                              @PathVariable String keyword,
                                                              @ApiParam(name = "pageNo", value = "返回的库的页码，默认填1")
                                                              @PathVariable int pageNo,
                                                              @ApiParam(name = "pageSize", value = "联想的候选条数，越大提示内容越多")
                                                              @PathVariable int pageSize) throws IOException {
        return searchService.search(index, keyword, pageNo, pageSize);

    }

//    @RequestMapping("/import/paintings")
//    public void importPaintings() throws IOException {
//        searchService.importAllPaintings();
//    }


}
