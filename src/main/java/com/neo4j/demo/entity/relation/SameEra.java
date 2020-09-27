package com.neo4j.demo.entity.relation;

import com.neo4j.demo.entity.node.Painter;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "SAME_ERA")
public class SameEra {
    @Id
    @GeneratedValue
    private Long id;
    @StartNode
    private Painter painterFrom;
    @EndNode
    private Painter painterTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Painter getPainterFrom() {
        return painterFrom;
    }

    public void setPainterFrom(Painter painterFrom) {
        this.painterFrom = painterFrom;
    }

    public Painter getPainterTo() {
        return painterTo;
    }

    public void setPainterTo(Painter painterTo) {
        this.painterTo = painterTo;
    }

    public SameEra() {
    }

    public SameEra(Long id, Painter painterFrom, Painter painterTo) {
        this.id = id;
        this.painterFrom = painterFrom;
        this.painterTo = painterTo;
    }
}
