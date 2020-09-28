package com.neo4j.demo.service;

import com.neo4j.demo.entity.node.Painter;
import com.neo4j.demo.repository.PainterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PainterServiceImpl implements PainterService {
    @Autowired
    PainterRepository repository;

    public PainterServiceImpl(PainterRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Painter> findAll() {
        return repository.findAll();
    }

    @Override
    public String findSameEraPainters(String name) {
        Painter painter = repository.findByName(name);
        final Long Id = painter.getId();
        List<Painter> painters = repository.findByNameAndSameEraPainters(painter.getName());
        StringBuilder builder = new StringBuilder("nodes:["+'\'');
        for (Painter p :
                painters) {
            builder.append(p.toString())
                    .append(",");
        }
        builder.append("],"+'\''+"links:["+'\'');
        for (Painter p :
                painters) {
            builder.append("{"+'\'')
                    .append("from:"+Id+'\'')
                    .append("to:"+p.getId()+'\'')
                    .append("text:"+"同一历史时期")
                    .append("}");
        }
        return builder.toString();
    }
}
