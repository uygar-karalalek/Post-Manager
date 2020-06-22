package org.uygar.postit.data.database;

import org.uygar.postit.data.database.queries.Query;
import org.uygar.postit.data.structures.PostContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMiner {

    public DatabaseConnection dbConnection;
    public ResultSet response;

    public DataMiner() {
        this.dbConnection = new DatabaseConnection(PostContainer.DB_PATH);
    }

    public void execute(Query query) throws SQLException {
        final String SQL_QUERY = query.getQuery();
        dbConnection
                .getConnection()
                .createStatement().execute(SQL_QUERY);
        query.clear();
    }

    public void executeQuery(Query query) {
        final String SQL_QUERY = query.getQuery();
        try {
            response = dbConnection
                    .getConnection()
                    .createStatement().executeQuery(SQL_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            query.clear();
        }
    }

    public Map<String, List<String>> getListOfResult() {
        Map<String, List<String>> result = new HashMap<>();
        if (response == null)
            throw new UnsupportedOperationException("Devi chiamare una query di selezione!");
        try {
            int cols = response.getMetaData().getColumnCount();
            for (int i = 1; i <= cols; i++)
                result.put(response.getMetaData().getColumnName(i), new ArrayList<>());
            while (response.next())
                for (String column : result.keySet())
                    result.get(column).add(response.getString(column));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean tryExecute(Query query) {
        try {
            this.execute(query);
            return true;
        } catch (SQLException e) {
        }
        return false;
    }

}