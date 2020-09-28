package com.neo4j.demo.repository;

import com.neo4j.demo.entity.node.Painter;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PainterRepository extends Neo4jRepository<Painter,Long> {
    @Query("MATCH (painter1 : Painter) WHERE painter1.name = {name} RETURN painter1;")
    Painter findByName(@Param("name") String name);
    @Query("MATCH (painter1 : Painter)-[SAME_ERA]->(painter2 : Painter) WHERE painter1.name = {name} RETURN painter1,painter2")
    List<Painter> findByNameAndSameEraPainters(@Param("name") String name);
    @Query("MATCH (p : Painter) RETURN p")
    List<Painter> findAll();
}
