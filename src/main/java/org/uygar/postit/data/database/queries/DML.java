package org.uygar.postit.data.database.queries;

public interface DML extends Query {

    DML insert();
    DML into(String tableName);
    DML into(String tableName, String...cols);
    DML values(String...values);

    DML update(String tableName);
    DML set(String col, String value);
    DML set(String...cols_vals);

    DML delete();
    DML from(String tableName);
    DML where();
    DML where(String condition);
    DML field(String field);
    DML like(String value);
    DML equalsTo(String value);
    DML is(String operator, String value);

}