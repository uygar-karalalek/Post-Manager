package org.uygar.postit.data.database.queries;

public class DQLQuery implements DQL {

    private String query = "";

    @Override
    public Query select(String... tables) {
        String fields = "SELECT " + tables[0];
        for (int i = 1; i < tables.length; i++)
            fields += ", " + tables[1];
        this.query = fields;
        return this;
    }

    @Override
    public Query selectAll() {
        this.query += "SELECT *";
        return this;
    }

    @Override
    public Query from(String table) {
        this.query += " FROM " + table;
        return this;
    }

    @Override
    public Query where() {
        this.query += " WHERE ";
        return this;
    }

    @Override
    public Query where(String condition) {
        this.query += " WHERE " + condition;
        return this;
    }

    @Override
    public Query field(String field) {
        this.query += " " + field;
        return this;
    }

    @Override
    public Query like(String valueOfField) {
        this.query += " LIKE '" + valueOfField + "'";
        return this;
    }

    @Override
    public Query equalsTo(String valueOfField) {
        this.query += "='" + valueOfField + "'";
        return this;
    }

    @Override
    public Query is(String operator, String value) {
        this.query += operator + "'" + value + "'";
        return null;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "DQLQuery{" +
                "query: '" + query + '\'' +
                '}';
    }

}