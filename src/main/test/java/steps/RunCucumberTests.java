package steps;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        tags = "not @Ignore", glue = "src/main/test/java_tests/steps/",
        features = "src/main/test/java_tests/features/")
public class RunCucumberTests {}