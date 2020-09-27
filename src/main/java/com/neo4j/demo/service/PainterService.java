package com.neo4j.demo.service;

import com.neo4j.demo.entity.node.Painter;
import com.neo4j.demo.repository.PainterRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public interface PainterService {
    public String findSameEraPainters(String name);
}
