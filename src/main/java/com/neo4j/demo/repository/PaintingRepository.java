package com.neo4j.demo.repository;

import com.neo4j.demo.entity.node.Painting;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PaintingRepository extends Neo4jRepository<Painting,Long> {
    @Query("MATCH (p1 : Painting) WHERE p1.name = {name} RETURN p1;")
    Painting findByName(@Param("name") String name);

    @Query("MATCH (p1 : Painting) WHERE p1.painter_name={makerName} RETURN p1;")
    Painting findByMakerName(@Param("makerName") String makerName);


    Collection<Painting> findByType(@Param("type") String type);





}
