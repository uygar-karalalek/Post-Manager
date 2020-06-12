package steps;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(plugin = {"pretty"}, tags = "not @Ignore")
@RunWith(Cucumber.class)
public class RunCucumberTests {

}
