package org.uygar.postit.data.query_utils;

import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.queries.DMLQueryBuilder;
import org.uygar.postit.data.database.queries.DQLQueryBuilder;
import org.uygar.postit.data.database.queries.Query;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.controllers.post.postit.PostItUtils;

import java.time.LocalDateTime;
import java.util.Optional;

public class QueryUtils {

    public static boolean tryRemovePostItFromDB(DataMiner miner, PostIt postIt) {
        Query query = new DMLQueryBuilder().delete().from("postit")
                .where().field("creationDate").equalsTo(postIt.getDataCreazione().toString());
        return miner.tryExecute(query);
    }

    public static boolean tryModifyPostItOnDB(DataMiner miner, PostIt postIt) {
        try {
            Query query = new DMLQueryBuilder().update("postit")
                    .set("priority", Integer.toString(postIt.getPriority()),
                            "colore", postIt.getColore().toString(),
                            "text", postIt.getTesto(),
                            "title", postIt.getTitolo(),
                            "endDate", postIt.getDataScadenza().toString())
                    .where().field("creationdate").equalsTo(postIt.getDataCreazione().toString());
            return miner.tryExecute(query);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean tryCreateNewPostItOnDB(DataMiner miner, PostIt postIt) {
        // DB Order: id, priority, colore, postid, creationdate, text, endDate, title, done
        try {
            Query query = new DMLQueryBuilder()
                    .insert()
                    .into("postit")
                    .values("null",
                            Integer.toString(postIt.getPriority()),
                            postIt.getColore().toString(),
                            Integer.toString(postIt.getPostFatherId()),
                            postIt.getDataCreazione().toString(),
                            postIt.getTesto(),
                            postIt.getDataScadenza().toString(),
                            postIt.getTitolo(),
                            Boolean.toString(postIt.isFatto()));
            return miner.tryExecute(query);
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean tryCreateNewPost(DataMiner miner, Post post) {
        DMLQueryBuilder query = new DMLQueryBuilder();
        query.insert().into("post").values(
                "null", // visto che c'è l'auto increment
                post.getName().trim(),
                post.getSortType().toString(),
                post.getCreationDate().toString(),
                post.getCreationDate().toString()); // l'ultima modifica è la volta in cui lo crei

        return miner.tryExecute(query);
    }

    public static Integer getLastCreatedPostId(DataMiner miner) {
        DQLQueryBuilder dql = new DQLQueryBuilder();
        dql.select("MAX(id) as id").from("post");
        miner.executeQuery(dql);
        String result = miner.getMappedListOfResult().get("id").get(0);
        return Integer.parseInt(result);
    }

    public static void setDoneStateOfPostItInDatabase(DataMiner miner, PostIt postIt) {
        Query query = new DMLQueryBuilder()
                .update("postit")
                .set("done", Boolean.toString(postIt.isFatto()))
                .where("id=" + postIt.getId());
        miner.tryExecute(query);
    }

}