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
    private int created_time;
    private String name;
    private String museum;
    private String makerName;
    private String painter_name;
    private String picture;
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Painting painting = (Painting) o;
        return created_time == painting.created_time &&
                Objects.equals(id, painting.id) &&
                Objects.equals(name, painting.name) &&
                Objects.equals(museum, painting.museum) &&
                Objects.equals(makerName, painting.makerName) &&
                Objects.equals(painter_name, painting.painter_name) &&
                Objects.equals(picture, painting.picture) &&
                Objects.equals(type, painting.type) &&
                Objects.equals(sameMuseumPaintings, painting.sameMuseumPaintings) &&
                Objects.equals(maker, painting.maker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created_time, name, museum, makerName, painter_name, picture, type, sameMuseumPaintings, maker);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCreated_time() {
        return created_time;
    }

    public void setCreated_time(int created_time) {
        this.created_time = created_time;
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

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public String getPainter_name() {
        return painter_name;
    }

    public void setPainter_name(String painter_name) {
        this.painter_name = painter_name;
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

    public Painting(Long id, int created_time, String name, String museum, String makerName, String painter_name, String picture, String type, List<Painting> sameMuseumPaintings, Painter maker) {
        this.id = id;
        this.created_time = created_time;
        this.name = name;
        this.museum = museum;
        this.makerName = makerName;
        this.painter_name = painter_name;
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