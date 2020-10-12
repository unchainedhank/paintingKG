package com.neo4j.demo.entity;

import org.neo4j.ogm.annotation.*;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Objects;

@NodeEntity
public class Painting {
    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "created_time")
    private String createdTime;//绘画时间
    @Field(analyzer = "ik_max_word")
    private String name;//画名
    @Field(analyzer = "ik_max_word")
    private String museum;//所在博物馆
    private String picture;//图像url地址
    private String type;//绘画类型
    @Property(name = "painter_name")
    private String maker;
    @Property(name = "painter_id")
    private Long makerId;

    @Override
    public String toString() {
        return "Painting{" +
                "id=" + id +
                ", createdTime='" + createdTime + '\'' +
                ", name='" + name + '\'' +
                ", museum='" + museum + '\'' +
                ", picture='" + picture + '\'' +
                ", type='" + type + '\'' +
                ", maker='" + maker + '\'' +
                ", maker_id='" + makerId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Painting painting = (Painting) o;
        return Objects.equals(id, painting.id) &&
                Objects.equals(createdTime, painting.createdTime) &&
                Objects.equals(name, painting.name) &&
                Objects.equals(museum, painting.museum) &&
                Objects.equals(picture, painting.picture) &&
                Objects.equals(type, painting.type) &&
                Objects.equals(maker, painting.maker) &&
                Objects.equals(makerId, painting.makerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdTime, name, museum, picture, type, maker, makerId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
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

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public Long getMakerId() {
        return makerId;
    }

    public void setMakerId(Long makerId) {
        this.makerId = makerId;
    }

    public Painting() {
    }

    public Painting(Long id, String createdTime, String name, String museum, String picture, String type, String maker, Long makerId) {
        this.id = id;
        this.createdTime = createdTime;
        this.name = name;
        this.museum = museum;
        this.picture = picture;
        this.type = type;
        this.maker = maker;
        this.makerId = makerId;
    }
}