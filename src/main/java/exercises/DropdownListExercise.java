package exercises;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class DropdownListExercise extends Exercise {
    private static final String URL = "https://antycaptcha.amberteam.pl/exercises/exercise3?seed=%s";

    private static final By SELECT_LOCATION = new By.ById("s13");

    public DropdownListExercise(WebDriver driver) {
        super(driver);
    }

    public String generateUrl(String seed) {
        return String.format(URL, seed);
    }

    private String findOption(WebElement row) {
        return row.findElements(COLUMNS_LOCATION).get(1)
                .findElement(CODE_LOCATION).getText();
    }

    public void chooseOption() {
        List<WebElement> rows = getScenarioRows();
        findExpectedOutcome(rows);
        String option = findOption(rows.get(0));
        System.out.println("\tScenario: ");
        System.out.println(String.format("\t\tStep 1: In the following dropdown choose %s colour.", option));
        System.out.println("\t\tExpected outcome: " + expectedOutcome);
        Select select = new Select(driver.findElement(SELECT_LOCATION));
        select.selectByVisibleText(option);
    }

}
