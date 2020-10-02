package com.neo4j.demo.util;

import com.neo4j.demo.entity.node.Painter;
import com.neo4j.demo.entity.node.Painting;

import java.util.List;

public class NodeUtil {
    public static String convert2String(List<Painter> painters, List<Painting> paintings) {
        Long sourceId = painters.get(0).getId();
        StringBuilder builder = new StringBuilder("nodes:["+'\'');
        for (Painter p :
                painters) {
            builder.append(p.toString())
                    .append(",");
        }
        for (Painting p1 :
                paintings) {
            builder.append(p1.toString())
                    .append(",");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("],"+'\''+"links:["+'\'');
        for (Painter p :
                painters) {
            builder.append("{" + '\'').append("from:").append(sourceId).append('\'').append("to:").append(p.getId()).append('\'')
                    .append("text:"+"同一历史时期")
                    .append("}")
                    .append(",");
        }
        for (Painting p1 :
                paintings) {
            builder.append("{" + '\'').append("from:").append(sourceId).append('\'').append("to:").append(p1.getId()).append('\'')
                    .append("text:"+"创作")
                    .append("}")
                    .append(",");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("]");
        return builder.toString();
    }
    public static String convert2String(List<Painting> paintings) {
        Long sourceId = paintings.get(0).getId();
        StringBuilder builder = new StringBuilder("nodes:["+'\'');
        for (Painting p1 :
                paintings) {
            builder.append(p1.toString())
                    .append(",");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("],"+'\''+"links:["+'\'');
        for (Painting p1 :
                paintings) {
            builder.append("{" + '\'').append("from:").append(sourceId).append('\'').append("to:").append(p1.getId()).append('\'')
                    .append("text:"+"同一博物馆")
                    .append("}")
                    .append(",");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("]");
        return builder.toString();
    }
}
