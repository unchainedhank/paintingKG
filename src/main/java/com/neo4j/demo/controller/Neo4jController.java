package com.neo4j.demo.controller;

import com.neo4j.demo.repository.PainterRepository;
import com.neo4j.demo.service.PainterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/painters")
public class Neo4jController {
//    PainterRepository painterRepository;
    @Autowired
    PainterServiceImpl service;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String findSameEraPainters(@PathVariable String name) {
        return service.findSameEraPainters(name);
    }

//    public String findPainterByName(@PathVariable String name) {
//        return painterRepository.findByNameAndSameEraPainters(name).toString();
//    }




    @RequestMapping(value = "/")
    //测试用例
    @GetMapping("hello")
    public String hello() {
        return "hello";
    }


//    @GetMapping("/h")
//    public Painter getPainterByName(String name) {
//        return service.findPainterByName(name);

//    @RequestMapping(method = RequestMethod.GET)
//    public Painter getPainterByName(@RequestHeader(value = "name") String name) {
//        return service.findPainterByName(name);
//        ("米开朗基罗·梅里西·德·卡拉瓦乔");
}
