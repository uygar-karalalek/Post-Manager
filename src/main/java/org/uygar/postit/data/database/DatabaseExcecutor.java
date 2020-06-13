package org.uygar.postit.data.database;

import org.uygar.postit.data.database.queries.Query;

import java.sql.Connection;

public class DatabaseExcecutor {

    public Connection connection;

    public void execute(Query query) {
        final String SQL_QUERY = query.getQuery();
    }

}