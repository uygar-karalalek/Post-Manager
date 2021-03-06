package org.uygar.postit.data.structures;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.queries.DQL;
import org.uygar.postit.data.database.queries.DQLQueryBuilder;

import java.util.*;

public abstract class BaseDataContainer<T> implements Container, Iterable<T> {

    protected DataMiner dataMiner;
    protected ObservableList<T> list = FXCollections.observableArrayList();

    public BaseDataContainer(DataMiner dataMiner) {
        this.dataMiner = dataMiner;
    }

    protected void loadDataFromTable(String tableName, Optional<String> whereCondition) {
        DQL dql = new DQLQueryBuilder().selectAll().from(tableName);
        whereCondition.ifPresent(dql::where);
        dataMiner.executeQuery(dql);

        Map<String, List<String>> sqlObjects = dataMiner.getMappedListOfResult();
        int numOfItems = getNumOfItems(tableName, whereCondition);

        addAll(parseFromAbstractSQLObject(sqlObjects, numOfItems));
    }

    protected int getNumOfItems(String tableName, Optional<String> whereCondition) {
        DQL dql = new DQLQueryBuilder();
        String countAlias = "'id'";
        dql.select("count(id) as " + countAlias).
                from(tableName);
        whereCondition.ifPresent(dql::where);

        dataMiner.executeQuery(dql);
        return Integer.parseInt(dataMiner.getMappedListOfResult().get("id").get(0));
    }

    public void add(T element) {
        list.add(element);
    }

    public void addAll(List<T> elements) {
        list.addAll(elements);
    }

    public void remove(int index) {
        list.remove(index);
    }

    public void remove(T element) {
        int index = 0;
        for (T currentElement : list) {
            if (!currentElement.equals(element)) index++;
            else break;
        }
        list.remove(index);
    }

    protected abstract List<T> parseFromAbstractSQLObject(Map<String, List<String>> stringMappedObj, int numOfElements);

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return list.listIterator();
    }

    @Override
    public List<T> getList() {
        return list;
    }

    public DataMiner getDataMiner() {
        return dataMiner;
    }

    public ObservableList<T> getObservableList() {
        return list;
    }

}