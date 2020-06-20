package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.DatabaseConnection;
import org.uygar.postit.data.database.queries.DMLQueryBuilder;
import org.uygar.postit.data.structures.PostContainer;
import org.uygar.postit.post.Post;
import setup.Config;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContainerSteps {

    DataMiner dataMiner;
    DMLQueryBuilder query;
    Config config;
    PostContainer postContainer;

    public ContainerSteps(DataMiner dataMiner) {
        this.config = new Config();
        this.query = new DMLQueryBuilder();
        this.dataMiner = dataMiner;
        this.dataMiner.dbConnection = new DatabaseConnection(config.getDbURI());
    }

    @Given("a post in db")
    public void aPostInDB(DataTable dataTable) {
        Map<String, String> post = dataTable.asMaps().get(0);
        query.insert().into("post").values(
                "null",
                post.get("name"),
                post.get("sort"),
                post.get("creationDate"),
                post.get("lastModifiedDate")
        );

        dataMiner.execute(query);
    }

    @When("it asks to get all the posts")
    public void itAsksToGetAllThePosts() {
        postContainer = new PostContainer(dataMiner);
    }

    @Then("it should print the posts")
    public void itShouldPrintThePosts() {
        Assert.assertNotNull(postContainer);
        Assert.assertNotNull(postContainer.getPostList());
        Assert.assertFalse(postContainer.getPostList().isEmpty());

        assertThat(postContainer.getPostList(),
                hasItem(Matchers.hasProperty("name", is("uygarPost"))));
    }

    @After("@FromDB")
    public void afterAskingDBData() {
        query.delete()
                .from("post")
                .where("name = 'uygarPost'");

        dataMiner.execute(query);
    }
}
