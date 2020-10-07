package com.neo4j.demo.repository;

import com.neo4j.demo.entity.Painter;
import com.neo4j.demo.entity.Painting;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PainterRepository extends Neo4jRepository<Painter,Long> {
    //查找5个最新的画家
    @Query("MATCH (p : Painter) RETURN p LIMIT 5;")
    List<Painter> findPainters();

    //查找n个最新的画家
    @Query("MATCH (p : Painter) RETURN p LIMIT {limit};")
    List<Painter> findLimitPainters(@Param("limit") int limit);

    //查找id画家
    @Query("MATCH (p : Painter) WHERE id(p)={id} RETURN p;")
    Painter findPainterById(@Param("id") Long id);

    //查找有关画家
    @Query("MATCH (p1 : Painter)-[]-(p2: Painter) WHERE id(p1)={id} RETURN p1,p2;")
    List<Painter> findRelatedPaintersById(@Param("id") Long id);

    //查找有关画作
    @Query("MATCH (p1 : Painter)-[]-(p2: Painting) WHERE id(p1)={id} RETURN p2;")
    List<Painting> findRelatedPaintingsByPainterId(@Param("id") Long id);
    
//    MATCH (p1 : Painter)-[]-(p2: Painter) WHERE id(p1)=216055 RETURN p1,p2;
//    MATCH (p1 : Painter)-[]-(p2: Painting) WHERE id(p1)=216055 RETURN p1,p2;
    
//    @Query("MATCH (painter1 : Painter) WHERE painter1.name = {name} RETURN painter1;")
//    Painter findByName(@Param("name") String name);
//    @Query("MATCH (painter1 : Painter)-[SAME_ERA]->(painter2 : Painter) WHERE painter1.name = {name} RETURN painter1,painter2")
//    List<Painter> findByNameAndSameEraPainters(@Param("name") String name);
//    @Query("MATCH (p : Painter) RETURN p")
//    List<Painter> findAll();

}
