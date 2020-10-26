package java_tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.DatabaseConnection;
import org.uygar.postit.data.database.queries.DQLQueryBuilder;
import java_tests.setup.Config;

import java.util.List;
import java.util.Map;

public class DQLQueriesSteps {

    DataMiner dataMiner;
    DQLQueryBuilder query;
    Config config;

    public DQLQueriesSteps(DataMiner dataMiner) {
        this.config = new Config();
        this.query = new DQLQueryBuilder();
        this.dataMiner = dataMiner;
    }

    @Given("a sqlite connection")
    public void a_sqlite_connection() {
        this.dataMiner.dbConnection = new DatabaseConnection(config.getDbURI());
    }

    @When("I ask data of column {string} of table {string}")
    public void i_ask_data_of_column_of_table(String columnName, String tableName) {
        query.select(columnName).from(tableName);
        dataMiner.executeQuery(query);
    }

    @Then("it should return the data")
    public void it_should_return_the_data() {
        Map<String, List<String>> result = dataMiner.getMappedListOfResult();

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
    }

}