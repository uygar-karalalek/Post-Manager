package org.uygar.postit.data.database.queries;

public class DQLQueryBuilder implements DQL {

    private String query = "";

    @Override
    public DQL select(String column, String... columns) {
        String fields = "SELECT " + column;
        for (int i = 0; i < columns.length; i++)
            fields += ", " + columns[i];
        this.query = fields;
        return this;
    }

    @Override
    public DQL selectAll() {
        this.query += "SELECT *";
        return this;
    }

    @Override
    public DQL from(String table) {
        this.query += " FROM " + table;
        return this;
    }

    @Override
    public DQL where() {
        this.query += " WHERE ";
        return this;
    }

    @Override
    public DQL where(String condition) {
        this.query += " WHERE " + condition;
        return this;
    }

    @Override
    public DQL field(String field) {
        this.query += " " + field;
        return this;
    }

    @Override
    public DQL like(String valueOfField) {
        this.query += " LIKE '" + valueOfField + "'";
        return this;
    }

    @Override
    public DQL equalsTo(String valueOfField) {
        this.query += "='" + valueOfField + "'";
        return this;
    }

    @Override
    public DQL is(String operator, String value) {
        this.query += operator + "'" + value + "'";
        return this;
    }

    @Override
    public void clear() {
        this.query = "";
    }

    public String getQuery() {
        return query+";";
    }

    @Override
    public String toString() {
        return "DQLQuery{" +
                "query: '" + query + '\'' +
                '}';
    }

}