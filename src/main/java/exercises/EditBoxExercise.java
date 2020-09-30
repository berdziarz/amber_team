package exercises;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EditBoxExercise extends Exercise {
    private static final String URL = "https://antycaptcha.amberteam.pl/exercises/exercise2?seed=%s";

    private static final By TEXT_BOX_LOCATION = new By.ById("t14");

    public EditBoxExercise(WebDriver driver) {
        super(driver);
    }

    public String generateUrl(String seed) {
        return String.format(URL, seed);
    }

    private String getTextToType() {
        List<WebElement> rows = getScenarioRows();
        findExpectedOutcome(rows);
        return rows.get(0)
                .findElements(COLUMNS_LOCATION).get(1)
                .findElements(CODE_LOCATION).get(0).getText();
    }

    public void writeText() {
        String text = getTextToType();
        System.out.println("\tScenario: ");
        System.out.println(String.format("\t\tStep 1: Enter text %s into t14 editbox", text));
        System.out.println(String.format("\t\tPress B1 button"));
        System.out.println("\t\tExpected outcome: " + expectedOutcome);
        waitAndType(TEXT_BOX_LOCATION, text);
        waitAndClick(BUTTON_1_LOCATION);
    }
}
