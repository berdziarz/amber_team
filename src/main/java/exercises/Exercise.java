package exercises;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Exercise {
    private static final long TIMEOUT = 3L;
    protected static final String CORRECT_RESULT_TEXT = "OK. Good answer";

    protected static final By BUTTON_SOLUTION_LOCATION = new By.ById("solution");
    protected static final By RESULT_LOCATION = new By.ByClassName("wrap");
    protected static final By BUTTON_1_LOCATION = new By.ById("btnButton1");
    protected static final By ROWS_LOCATION = new By.ByTagName("tr");
    protected static final By COLUMNS_LOCATION = new By.ByTagName("td");
    protected static final By CODE_LOCATION = new By.ByTagName("code");

    protected String expectedOutcome = StringUtils.EMPTY;
    protected WebDriver driver;
    private WebDriverWait wait;


    public Exercise(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEOUT);
    }

    public String getExpectedOutcome() {
        return expectedOutcome;
    }

    protected void findExpectedOutcome(List<WebElement> rows) {
        expectedOutcome = rows.get(rows.size() - 1)
                .findElements(COLUMNS_LOCATION).get(2)
                .findElement(CODE_LOCATION).getText();
    }

    protected List<WebElement> getScenarioRows() {
        List<WebElement> rows = driver.findElements(ROWS_LOCATION);
        rows.remove(0);
        return rows;
    }

    protected void waitAndClick(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    protected boolean waitForExpected(By by, String expected) {
        return wait.until(ExpectedConditions.textToBe(by, expected));

    }

    protected void waitAndType(By by, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(by)).sendKeys(text);
    }

    public boolean checkSolution() {
        waitAndClick(BUTTON_SOLUTION_LOCATION);
        return compareResult(CORRECT_RESULT_TEXT);
    }

    public boolean compareResult(String expected) {
        return waitForExpected(RESULT_LOCATION, expected);
    }
}
