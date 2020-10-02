package com.neo4j.demo.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends Neo4jRepository<Object,Long> {
    //根据id查找节点
    @Query("MATCH (p) WHERE id(p)={id} RETURN p;")
    public Node findNodeById(Long id);


}
