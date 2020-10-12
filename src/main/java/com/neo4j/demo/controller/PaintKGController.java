package com.neo4j.demo.controller;

import com.neo4j.demo.service.ElasticSearchService;
import com.neo4j.demo.service.PainterService;
import com.neo4j.demo.service.PaintingService;
import com.neo4j.demo.util.NodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@Api(value = "对知识图谱的crud", tags = "查询画家或画作")
public class PaintKGController {
    @Resource
    private PainterService painterService;
    @Resource
    private PaintingService paintingService;

    /**
     * 返回最近的5个Node
     * @param type 1:画家,2:画作
     * @return 5个Node
     */
    @ApiOperation(value = "返回最近的5个Node",notes = "用type指定返回类型 1:画家,2:画作")
    @RequestMapping(value = "/type", headers = "type", method = RequestMethod.GET)
    public List recentNode(
                @ApiParam(name = "type", value = "类型,1:画家,2:画作")
            @RequestHeader("type") int type) {
        return type==1? painterService.findPainters() : paintingService.findPaintings();
    }

    /**
     * 返回最近的limit个Node
     * @param type 类型,1:画家,2:画作
     * @param limit 限制的条数
     * @return limit个Node
     */
        @ApiOperation(value = "返回最近的limit个Node",notes = "用type指定返回类型 1:画家,2:画作")
    @RequestMapping(value = "/type-limit", headers = "type", method = RequestMethod.GET)
    public List recentLimitedNode(
                @ApiParam(name = "type", value = "类型,1:画家,2:画作")
            @RequestHeader("type") int type,
                @ApiParam(name = "limit", value = "限制的条数")
            @RequestHeader("limit") int limit) {
        return type ==1? painterService.findPainters(limit) : paintingService.findPaintings(limit);
    }


    /**
     * 用id查找节点，type确定类型
     * @param type 类型 1画家2画作
     * @param id 节点id
     * @return 节点
     */
        @ApiOperation(value = "用id查找节点，type确定类型")
    @RequestMapping(value = "/type-id", headers = {"type","id"}, method = RequestMethod.GET)
    public Object findNodeById(

                @ApiParam(name = "type", value = "1画家2画作")
            @RequestHeader("type") int type,

                @ApiParam(name = "id", value = "节点id")
            @RequestHeader("id") Long id) {
        return type==1? painterService.findPainterById(id) : paintingService.findPaintingById(id);
    }

    /**
     * 通过id查找节点及其相关的所有节点
     * @param type 类型 1画家2画作
     * @param id 节点id
     * @return 节点集合的json
     */
    @ApiOperation(value = "通过id查找节点及其相关的所有节点")
    @RequestMapping(value = "/id-graph", headers = {"id","type"}, method = RequestMethod.GET)
    public String findGraphById(@RequestHeader("type") int type, @RequestHeader("id") Long id) {
        return type == 1?
                NodeUtil.convert2String(painterService.findRelatedPainters(id), painterService.findRelatedPaintings(id)):
                NodeUtil.convert2String(paintingService.findRelatedPaintings(id));
        //        ObjectMapper mapper = new ObjectMapper();
//        return type==1?
//                mapper.writeValueAsString(painterService.findRelatedPainters(id))+mapper.writeValueAsString(painterService.findRelatedPaintings(id)):
//                mapper.writeValueAsString(paintingService.findRelatedPaintings(id));
    }


    @RequestMapping(value = "/painting-des", headers = {"id", "des"}, method = RequestMethod.POST)
    public String addPaintingDescription(@RequestHeader("id") Long id, @RequestHeader("des") String des) {
        return paintingService.savePaintingDescription(id, des);
    }

}
