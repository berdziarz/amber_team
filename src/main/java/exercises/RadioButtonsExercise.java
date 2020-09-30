package exercises;

import javafx.scene.control.RadioButton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RadioButtonsExercise extends Exercise {
    private static final String URL = "https://antycaptcha.amberteam.pl/exercises/exercise4?seed=%s";
    private static final String RADIO_BUTTON_TYPE = "radio";
    private static final String TYPE_ATTRIBUTE = "type";
    private static final String RADIO_BUTTON_NAME = "s%s";
    private static final String RADIO_BUTTON_VALUE = "v%s%s";

    private static final By RADIO_BUTTONS_LOCATION = new By.ByTagName("input");

    private static Map<String, String> radioButtonsValues;

    static {
        radioButtonsValues = new HashMap<>();
        radioButtonsValues.put("Beluga Brown", "0");
        radioButtonsValues.put("Mango Orange", "1");
        radioButtonsValues.put("Verdoro Green", "2");
        radioButtonsValues.put("Freudian Gilt", "4");
        radioButtonsValues.put("Pink Kong", "5");
        radioButtonsValues.put("Duck Egg Blue", "6");
        radioButtonsValues.put("Anti - Establishment Mint", "7");
        radioButtonsValues.put("Amberlite Firemist", "8");
    }

    public RadioButtonsExercise(WebDriver driver) {
        super(driver);
    }

    public String generateUrl(String seed) {
        return String.format(URL, seed);
    }

    public void selectButtons() {
        List<String> options = getOptions();
        System.out.println("\tScenario: ");
        for (int i =0 ; i<options.size(); i++) {
            String step = String.format("In the group %s choose %s.",i,options.get(i));
            System.out.println(String.format("\t\tStep %s: ",i+1) + step);
        }
        System.out.println("\t\tExpected outcome: " + expectedOutcome);
        List<WebElement> radioButtons = driver.findElements(RADIO_BUTTONS_LOCATION).stream()
                .filter(r -> RADIO_BUTTON_TYPE.equals(r.getAttribute(TYPE_ATTRIBUTE)))
                .collect(Collectors.toList());
        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);
            String buttonName = String.format(RADIO_BUTTON_NAME, i);
            String buttonValue = String.format(RADIO_BUTTON_VALUE, radioButtonsValues.get(option), i);
            radioButtons.stream().filter(r -> buttonName.equals(r.getAttribute("name")))
                    .filter(f -> buttonValue.equals(f.getAttribute("value")))
                    .findFirst().ifPresent(WebElement::click);
        }
    }

    private List<String> getOptions() {
        List<WebElement> rows = getScenarioRows();
        findExpectedOutcome(rows);
        List<String> options = new ArrayList<>();
        rows.forEach(r ->
                options.add(r.findElements(COLUMNS_LOCATION).get(1).findElement(CODE_LOCATION).getText())
        );
        return options;
    }
}
