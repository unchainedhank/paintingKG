package com.neo4j.demo.entity.node;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NodeEntity
public class Painting {
    @Id
    @GeneratedValue
    private Long id;
    private int createdTime;
    private String name;
    private String museum;
    private String picture;
    private String type;

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", name:'" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Painting painting = (Painting) o;
        return createdTime == painting.createdTime &&
                Objects.equals(id, painting.id) &&
                Objects.equals(name, painting.name) &&
                Objects.equals(museum, painting.museum) &&
                Objects.equals(picture, painting.picture) &&
                Objects.equals(type, painting.type) &&
                Objects.equals(sameMuseumPaintings, painting.sameMuseumPaintings) &&
                Objects.equals(maker, painting.maker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdTime, name, museum, picture, type, sameMuseumPaintings, maker);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuseum() {
        return museum;
    }

    public void setMuseum(String museum) {
        this.museum = museum;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Painting> getSameMuseumPaintings() {
        return sameMuseumPaintings;
    }

    public void setSameMuseumPaintings(List<Painting> sameMuseumPaintings) {
        this.sameMuseumPaintings = sameMuseumPaintings;
    }

    public Painter getMaker() {
        return maker;
    }

    public void setMaker(Painter maker) {
        this.maker = maker;
    }

    public Painting() {
    }

    public Painting(Long id, int createdTime, String name, String museum, String painter_name, String picture, String type, List<Painting> sameMuseumPaintings, Painter maker) {
        this.id = id;
        this.createdTime = createdTime;
        this.name = name;
        this.museum = museum;
        this.picture = picture;
        this.type = type;
        this.sameMuseumPaintings = sameMuseumPaintings;
        this.maker = maker;
    }

    /**
     * relations:
     *  painting SAME_MUSEUM painting
     *  painting MADE_BY painter
     */

    @Relationship(type = "SAME_MUSEUM", direction = Relationship.OUTGOING)
    private List<Painting> sameMuseumPaintings;

    @Relationship(type = "MADE_BY", direction = Relationship.OUTGOING)
    private Painter maker;

    public void addMaker(Painter painter) {
        if (this.maker == null)
            this.maker = painter;
    }

    public void addSameMuseumPainting(Painting painting) {
        if (this.sameMuseumPaintings == null)
            this.sameMuseumPaintings = new ArrayList<Painting>();
        this.sameMuseumPaintings.add(painting);
    }

}