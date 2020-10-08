package com.neo4j.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neo4j.demo.service.ElasticSearchService;
import com.neo4j.demo.service.PainterService;
import com.neo4j.demo.service.PaintingService;
import com.neo4j.demo.util.NodeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PaintKGController {
    @Resource
    private PainterService painterService;
    @Resource
    private PaintingService paintingService;
    @Resource
    private ElasticSearchService searchService;

    @RequestMapping(value = "/type", headers = "type", method = RequestMethod.GET)
    public List recentNode(@RequestHeader("type") int type) {
        return type==1? painterService.findPainters() : paintingService.findPaintings();
    }

    @RequestMapping(value = "/type-limit", headers = "type", method = RequestMethod.GET)
    public List recentLimitedNode(@RequestHeader("type") int type, @RequestHeader("limit") int limit) {
        return type ==1? painterService.findPainters(limit) : paintingService.findPaintings(limit);
    }

    @RequestMapping(value = "/type-id", headers = {"type","id"}, method = RequestMethod.GET)
    public Object findNodeById(@RequestHeader("type") int type, @RequestHeader("id") Long id) {
        return type==1? painterService.findPainterById(id) : paintingService.findPaintingById(id);
    }

    @RequestMapping(value = "/id-graph", headers = {"id","type"}, method = RequestMethod.GET)
    public String findGraphById(@RequestHeader("type") int type, @RequestHeader("id") Long id) {
        return type==1?
                NodeUtil.convert2String(painterService.findRelatedPainters(id), painterService.findRelatedPaintings(id)):
                NodeUtil.convert2String(paintingService.findRelatedPaintings(id));
        //        ObjectMapper mapper = new ObjectMapper();
//        return type==1?
//                mapper.writeValueAsString(painterService.findRelatedPainters(id))+mapper.writeValueAsString(painterService.findRelatedPaintings(id)):
//                mapper.writeValueAsString(paintingService.findRelatedPaintings(id));
    }
}
