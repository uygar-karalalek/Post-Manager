package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.DatabaseConnection;
import org.uygar.postit.data.database.queries.DQL;
import org.uygar.postit.data.database.queries.DQLQuery;
import setup.Config;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSteps {

    DataMiner dataMiner;
    Config config;

    public DatabaseSteps(DataMiner dataMiner) {
        config = new Config();
        this.dataMiner = dataMiner;
    }

    @Given("no sql connections")
    public void no_sql_connections() {

    }

    @When("I ask to connect to the database")
    public void i_ask_to_connect_to_the_database() {
        String databasePath = "jdbc:sqlite:post.db";
        this.dataMiner.dbConnection = new DatabaseConnection(databasePath);
    }

    @Then("it should connect to the database")
    public void it_should_connect_to_the_database() {
        Assert.assertNotNull(dataMiner);
        Assert.assertNotNull(dataMiner.dbConnection);
        Assert.assertNotNull(dataMiner.dbConnection.getConnection());
    }

}
