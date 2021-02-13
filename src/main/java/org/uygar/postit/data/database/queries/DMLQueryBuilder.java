package org.uygar.postit.data.database.queries;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.stream.Collectors;

public class DMLQueryBuilder implements DML {

    private String query = "";

    @Override
    public DML insert() {
        this.query += "INSERT";
        return this;
    }

    @Override
    public DML into(String tableName) {
        this.query += " INTO " + tableName;
        return this;
    }

    @Override
    public DML into(String tableName, String... cols) {
        this.query += " INTO " + tableName + "(" + cols[0];
        for (int i = 1; i < cols.length; i++)
            query += ", " + cols[i];
        this.query += ")";
        return this;
    }

    @Override
    public DML values(String... values) {
        convertValues(values);
        this.query += " VALUES (" + values[0];
        for (int i = 1; i < values.length; i++) {
            this.query += ", " + values[i];
        }
        this.query += ")";
        return this;
    }

    public void convertValues(String[] values) {
        for (int i = 0; i < values.length; i++)
            if (!values[i].equals("null"))
                values[i] = "'" + values[i].replace("'", "''") + "'";
    }

    @Override
    public DML update(String tableName) {
        this.query += "UPDATE " + tableName;
        return this;
    }

    @Override
    public DML set(String col, String value) {
        this.query += " SET " + col + " = '" + value + "'";
        return this;
    }

    @Override
    public DML set(String... cols_vals) {
        if (cols_vals.length % 2 != 0)
            throw new IllegalArgumentException("l'input dev'essere della forma colonna - valore!");
        this.query += " SET " + cols_vals[0] + " = '" + cols_vals[1] + "'";
        for (int i = 2; i < cols_vals.length; i += 2)
            this.query += ", " + cols_vals[i] + " = '" + cols_vals[i + 1].replace("'", "''") + "'";
        System.out.println(query);
        return this;
    }

    @Override
    public DML delete() {
        this.query += "DELETE";
        return this;
    }

    @Override
    public DML from(String tableName) {
        this.query += " FROM " + tableName;
        return this;
    }

    @Override
    public DML where() {
        this.query += " WHERE";
        return this;
    }

    @Override
    public DML where(String condition) {
        this.query += " WHERE " + condition;
        return this;
    }

    @Override
    public DML field(String field) {
        this.query += " " + field;
        return this;
    }

    @Override
    public DML like(String value) {
        this.query += " LIKE '" + value + "'";
        return this;
    }

    @Override
    public DML equalsTo(String value) {
        this.query += " = '" + value + "'";
        return this;
    }

    @Override
    public DML is(String operator, String value) {
        this.query += " " + operator + " '" + value + "'";
        return this;
    }

    @Override
    public void clear() {
        this.query = "";
    }

    @Override
    public String getQuery() {
        return this.query + ";";
    }

}