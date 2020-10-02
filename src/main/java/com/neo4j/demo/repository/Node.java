package com.neo4j.demo.repository;

import com.neo4j.demo.entity.node.Painter;
import com.neo4j.demo.entity.node.Painting;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class Node {
    private Painter painter;
    private Painting painting;
}
