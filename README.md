# paintingKG
A knowledgeGraph about famous paintings and painters.
# PaintKG

**paintKG**是一个名画和画家的知识图谱,包含500个节点和2000+属性和19k+关系

使用==Neo4j==做存储,==Spring Boot==启动,Spring Data Neo4j封装,Elastic Search做搜索,maven做依赖管理,swagger2做api测试和文档,前端使用Element UI和VUE

## 使用方法 How to Use

**Download zip**

or

~~~shell
giot clone https://github.com/unchainedhank/paintingKG.git
~~~

##  项目结构

~~~tex
├── PaintKGApplication.java
├── config
│   ├── ElasticSearchConfig.java
│   └── Swagger2.java
├── repository
│   ├── PainterRepository.java
│   └── PaintingRepository.java
├── service
│   ├── PainterService.java
│   ├── ElasticSearchService.java
│   └── PaintingService.java
├── entity
│   ├── Painter.java
│   └── Painting.java
├── controller
│   ├── ESQueryController.java
│   └── PaintKGController.java
~~~

项目使用MVVM结构,内部用repository->service->controller封装

数据在paintKG/painter和/paintKG/painting下

使用neo4j的import命令导入到你本地的neo4j里

### 导入数据

**导入作者**

~~~cyp
load csv with headers from "file:///painter.csv" as line
create (painter:Painter {name:line.painter_name,country:line.painter_country,birth:line.painter_birth,death:line.painter_death,description:line.painter_description,image:line.painter_image})
~~~

**导入作品**

~~~cyp
load csv with headers from "file:///painting.csv" as line
create (painting:Painting {name:line.painting_name,created_time:line.painting_time,museum:line.museum_name,type:line.type,picture:line.painting_url,painter_name:line.painter_name})
~~~

**添加属性和关系**

~~~cypher
#作品添加作者id字段
MATCH (p : Painting)-[MADE_BY]-(p1 : Painter) SET p.painter_id=id(p1) RETURN p;


#作者-作品关系
MATCH (painting:Painting),(painter:Painter) 
WHERE painting.painter_name = painter.name
CREATE (painting)-[made:MADE_BY]->(painter) 
RETURN made


#相同博物馆关系


match (painting1:Painting),(painting2:Painting) 
where painting1.museum = painting2.museum and painting1.museum <> "私人" and painting1.name <> painting2.name
create (painting1)-[same_museum:SAME_MUSEUM{place:painting1.museum}]->(painting2)
return same_museum

#字符串->int
MATCH (painter:Painter)
SET painter.birth = toInt(painter.birth)

MATCH (painter:Painter)
SET painter.death = toInt(painter.death)

MATCH (p : Painting)
SET p.created_time = toInt(p.created_time);


#相同时代

match (painter1:Painter),(painter2:Painter) 
where ABS(painter1.birth - painter2.birth) < 50 and painter1.name <> painter2.name
create (painter1)-[same_era:SAME_ERA]- >(painter2)
return same_era

~~~

接下来你可以自行对其进行CRUD
