package com.neo4j.demo.repository;

import com.neo4j.demo.entity.Painting;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PaintingRepository extends Neo4jRepository<Painting,Long> {
    //查找最新的5个画
    @Query("MATCH (p : Painting) RETURN p LIMIT 5;")
    List<Painting> findPaintings();

    //查找最新的n个画
    @Query("MATCH (p : Painting) RETURN p LIMIT {limit};")
    List<Painting> findLimitPaintings(@Param("limit") int limit);

    //查找id画
    @Query("MATCH (p : Painting) WHERE id(p)={id} RETURN p;")
    Painting findPaintingById(@Param("id") Long id);

    //查找有关的所有画
    @Query("MATCH (p1 : Painting)-[]-(p2: Painting) WHERE id(p1)={id} RETURN p1,p2;")
    List<Painting> findRelatedPaintingsById(@Param("id") Long id);

    //创建描述
    @Transactional
    @Query("MATCH (p : Painting) WHERE id(p)={id} SET p.description={description} RETURN p;")
    Painting savePaintingDescription(@Param("id") Long id, @Param("description") String description);

}
