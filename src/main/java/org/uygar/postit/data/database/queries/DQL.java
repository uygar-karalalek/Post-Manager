package org.uygar.postit.data.database.queries;

public interface DQL extends Query {

    Query select(String...tables);
    Query selectAll();
    Query from(String table);
    Query where();
    Query where(String condition);
    Query field(String field);
    Query like(String valueOfField);
    Query equalsTo(String valueOfField);
    Query is(String operator, String value);

}