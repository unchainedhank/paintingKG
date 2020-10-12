package com.neo4j.demo.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NodeEntity
public class Painting {
    @Id
    @GeneratedValue
    private Long id;
    private int createdTime;//绘画时间
    @Field(analyzer = "ik_max_word")
    private String name;//画名
    @Field(analyzer = "ik_max_word")
    private String museum;//所在博物馆
    private String picture;//图像url地址
    private String type;//绘画类型

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
                Objects.equals(maker, painting.maker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdTime, name, museum, picture, type, maker);
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
        this.maker = maker;
    }

    /**
     * relations:
     *  painting SAME_MUSEUM painting
     *  painting MADE_BY painter
     */



    @Relationship(type = "MADE_BY", direction = Relationship.OUTGOING)
    private Painter maker;

    public void addMaker(Painter painter) {
        if (this.maker == null)
            this.maker = painter;
    }



}