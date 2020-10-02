package com.neo4j.demo.controller;

import com.neo4j.demo.entity.node.Node;
import com.neo4j.demo.service.NodeService;
import com.neo4j.demo.service.PainterService;
import com.neo4j.demo.service.PaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class PaintKGController {
//    PainterRepository painterRepository;
    @Resource
    private PainterService painterService;
    @Resource
    private PaintingService paintingService;
    @Resource
    private NodeService nodeService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/type", headers = "type", method = RequestMethod.GET)
    public List recentNode(@RequestHeader("type") int type) {
        return type==1? painterService.findPainters() : paintingService.findPaintings();
    }

    @RequestMapping(value = "/type-limit", headers = "type", method = RequestMethod.GET)
    public List recentLimitedNode(@RequestHeader Map<String,Integer> typeAndLimit) {
        int type = Integer.parseInt(request.getHeader("type"));
        int limit = Integer.parseInt(request.getHeader("limit"));
        return type ==1? painterService.findPainters(limit) : paintingService.findPaintings(limit);
    }

    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public Node findNodeById(@RequestHeader("id") Long id) {
//        id = Long.parseLong(request.getHeader("id"));
        Node node = nodeService.findNodeById(id);
        return node;
    }

    @RequestMapping(value = "/id-graph", params = {"id"}, method = RequestMethod.GET)
    public List findGraphById(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        List list = new ArrayList(painterService.findRelatedPainters(id));
        list.addAll(paintingService.findRelatedPaintings(id));
        return list;
    }
}
