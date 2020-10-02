package com.neo4j.demo.service;

import com.neo4j.demo.entity.node.Node;
import com.neo4j.demo.entity.node.Painter;
import com.neo4j.demo.repository.NodeRepository;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class NodeService {
    @Resource
    private NodeRepository repository;

    public Node findNodeById(Long id) {
        return repository.findNodeById(id);
    }


}
