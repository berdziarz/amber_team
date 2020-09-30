package exercises;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.ArrayList;
import java.util.List;

public class TreeButtonsExercise extends Exercise {
    private static final String URL = "https://antycaptcha.amberteam.pl/exercises/exercise1?seed=%s";
    private static final String BUTTON_1_TEXT = "B1";

    private static final By BUTTON_2_LOCATION = new By.ById("btnButton2");

    public TreeButtonsExercise(WebDriver driver) {
        super(driver);
    }

    public String generateUrl(String seed) {
        return String.format(URL, seed);
    }

    private List<String> getSteps() {
        List<WebElement> rows = getScenarioRows();
        findExpectedOutcome(rows);
        List<String> steps = new ArrayList<>();
        rows.forEach(r ->
                steps.add(r.findElements(COLUMNS_LOCATION).get(1).findElement(CODE_LOCATION).getText())
        );
        return steps;
    }

    public void clickButtons() {
        List<String> steps = getSteps();
        System.out.println("\tScenario: ");
        for (int i =0 ; i<steps.size(); i++) {
            System.out.println(String.format("\t\tStep %s: ",i+1) + "Press button " + steps.get(i));
        }
        System.out.println("\t\tExpected outcome: " + expectedOutcome);
        steps.forEach(s -> {
            By button = BUTTON_1_TEXT.equals(s) ? BUTTON_1_LOCATION : BUTTON_2_LOCATION;
            waitAndClick(button);
        });

    }

}
