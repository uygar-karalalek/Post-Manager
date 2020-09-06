package java_tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.data.database.DatabaseConnection;
import java_tests.setup.Config;

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
        this.dataMiner.dbConnection = new DatabaseConnection(config.getDbURI());
        System.out.println(this.dataMiner.dbConnection.getConnection());
    }

    @Then("it should connect to the database")
    public void it_should_connect_to_the_database() {
        Assert.assertNotNull(dataMiner);
        Assert.assertNotNull(dataMiner.dbConnection);
        Assert.assertNotNull(dataMiner.dbConnection.getConnection());
    }

}
