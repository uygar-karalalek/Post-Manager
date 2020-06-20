package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.queries.DMLQueryBuilder;
import setup.Config;

import java.util.List;
import java.util.Map;

public class DMLQueriesSteps {

    DataMiner dataMiner;
    DMLQueryBuilder query;
    Config config;

    public DMLQueriesSteps(DataMiner dataMiner) {
        this.config = new Config();
        this.query = new DMLQueryBuilder();
        this.dataMiner = dataMiner;
    }

    @When("it asks to insert a post")
    public void it_asks_to_insert_a_post(DataTable dataTable) {
        Map<String, String> post = dataTable.asMaps().get(0);

        DMLQueryBuilder query = new DMLQueryBuilder();
        query.insert().into("post").values(
                post.get("id"),
                post.get("name"),
                post.get("sort"),
                post.get("creationDate"),
                post.get("lastModifiedDate")
        );

        this.dataMiner.execute(query);
    }

    @Then("it should insert the data")
    public void it_should_insert_the_data() {
        Map<String, List<String>> result = dataMiner.getListOfResult();

        Assert.assertTrue(result.get("name").contains("test"));
        Assert.assertNotNull(result);
    }

    @When("it asks to update a post")
    public void it_asks_to_update_a_post(DataTable table) {
        Map<String, String> post = table.asMaps().get(0);

        String id = post.get("id");
        String newName = post.get("name");

        query.update("post")
                .set("name", newName)
                .where().field("id").equalsTo(id);

        dataMiner.execute(query);
    }

    @Then("it updates the post with name {string}")
    public void it_updates_the_post(String newName) {
        Map<String, List<String>> result = dataMiner.getListOfResult();

        List<String> names = result.get("name");

        Assert.assertTrue(names.contains(newName));
    }

    @When("it asks to delete the post with name {string}")
    public void it_asks_to_delete_the_post_with_name(String postName) {
        query.delete().from("post").where().field("name").like(postName);

        dataMiner.execute(query);
    }

    @Then("it deletes the post")
    public void it_deletes_the_post() {

    }

}