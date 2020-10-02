package com.neo4j.demo.service;

import com.neo4j.demo.entity.node.Painter;
import com.neo4j.demo.entity.node.Painting;
import com.neo4j.demo.repository.PainterRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PainterService<T> {
    @Resource
    private PainterRepository repository;

    public List<Painter> findPainters() {
        return repository.findPainters();
    }

    public List<Painter> findPainters(int limit) {
        return repository.findLimitPainters(limit);
    }

    public Painter findPainterById(Long id) {
        return repository.findPainterById(id);
    }

    public List<Painter> findRelatedPainters(Long id) {
        return repository.findRelatedPaintersById(id);
    }

    public List<Painting> findRelatedPaintings(Long id) {
        return repository.findRelatedPaintingsByPainterId(id);
    }
}
