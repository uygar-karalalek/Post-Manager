package org.uygar.postit.data.database.queries;

public interface DQL extends Query {

    DQL select(String column, String...columns);
    DQL selectAll();
    DQL from(String table);
    DQL where();
    DQL where(String condition);
    DQL field(String field);
    DQL like(String valueOfField);
    DQL equalsTo(String valueOfField);
    DQL is(String operator, String value);

}