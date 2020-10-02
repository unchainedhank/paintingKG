package com.neo4j.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.neo4j.demo.entity.node.Node;

public class DomainNode {
    //序列化顺序
    @JsonPropertyOrder(value = {"painter", "name"})
    //为空的不做序列化
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Node node;
}
