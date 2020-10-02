package com.neo4j.demo.entity.node;

import org.neo4j.ogm.annotation.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@NodeEntity(label = "Painter")
public class Painter {
    @Id
    @GeneratedValue
    private Long id;//主键id
    @Property(name = "name")
    private String name;//姓名
    @Property(name = "country")
    private String country;//国家
    @Property(name = "birth")
    private int birth;//出生年份
    @Property(name = "death")
    private int death;//死亡年份
    @Property(name = "description")
    private String description;//描述
    @Property(name = "image")
    private String image;//图像

    /**
     * relations:
     *  painter SAME_ERA painter
     */

    @Relationship(type = "SAME_ERA", direction = Relationship.OUTGOING)
    private List<Painter> sameEraPainters;

    public void addSameEraPainters(List<Painter> painters) {
        if (this.sameEraPainters == null)
            this.sameEraPainters = painters;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +"\n"+
                ", name:'" + name + '\'' +"\n"+
                ", country:'" + country + '\'' +"\n"+
                ", birth:" + birth +"\n"+
                ", death:'" + death + '\'' +"\n"+
                ", description:'" + description + '\'' +"\n"+
                ", image:'" + image + '\'' +"\n"+
                ", sameEraPainters:" + sameEraPainters +"\n"+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Painter painter = (Painter) o;
        return Objects.equals(id, painter.id) &&
                Objects.equals(name, painter.name) &&
                Objects.equals(country, painter.country) &&
                Objects.equals(birth, painter.birth) &&
                Objects.equals(death, painter.death) &&
                Objects.equals(description, painter.description) &&
                Objects.equals(image, painter.image) &&
                Objects.equals(sameEraPainters, painter.sameEraPainters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, birth, death, description, image, sameEraPainters);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getBirth() {
        return birth;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Painter> getSameEraPainters() {
        return sameEraPainters;
    }

    public void setSameEraPainters(List<Painter> sameEraPainters) {
        this.sameEraPainters = sameEraPainters;
    }

    public Painter() {
    }

    public Painter(Long id, String name, String country, int birth, int death, String description, String image, List<Painter> sameEraPainters) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.birth = birth;
        this.death = death;
        this.description = description;
        this.image = image;
        this.sameEraPainters = sameEraPainters;
    }
}
