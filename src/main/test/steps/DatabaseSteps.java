package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.uygar.postit.data.database.DatabaseConnection;

public class DatabaseSteps {

    private DatabaseConnection connection;

    @Given("a sqlite connection")
    public void aSqliteConnection() throws ClassNotFoundException {
        Class.forName("org.sqlite.SQLiteConnection");
    }

    @When("I ask to connect to the database path {string}")
    public void iAskToConnectToTheDatabasePath(String dbPath) {
        connection = new DatabaseConnection(dbPath);
    }

    @Then("it should connect to the database")
    public void itShouldConnectToTheDatabase() {
        Assert.assertNotNull(connection);
        Assert.assertNotNull(connection.getConnection());
    }

}
