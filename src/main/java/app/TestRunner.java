package app;

import certificate.Certificate;
import certificate.CertificateController;
import certificate.CertificateResponse;
import exercises.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.http.HttpResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class TestRunner {
    private static final String ARG = "headless";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String WHITE = "\u001B[0m";
    private static final String HOME_PAGE_URL = "https://antycaptcha.amberteam.pl/";
    private static final String ORGANIZATION_TEST_VALUE = "test_Organization";
    private static final String NAME_TEST_VALUE = "test_Name";
    private static final String NEW_NAME_TEST_VALUE_2 = "test_New_Name";
    private static final String PERIOD_TEST_VALUE = "test_Period";
    private static final String TRADE_TEST_VALUE = "test_Trade";

    private static final long IMPLICITLY_WAIT = 10;

    private static final int CODE_200 = 200;
    private static final int CODE_201 = 201;
    private static final int CODE_404 = 404;
    private static final int CODE_500 = 500;

    private static final By SEED_LOCATION = new By.ByTagName("em");

    private static WebDriver driver;
    private static String seed;
    private static int success =0;
    private static int fail=0;

    public static void main(String[] args) throws IOException, URISyntaxException {
        boolean headless = args.length > 0 && ARG.equals(args[0]);
        init(headless);
        System.out.println("1. Exercises for test automation of web applications\n");
        try {
            System.out.println("  \"test: THREE BUTTONS\"");
            TreeButtonsExercise treeButtons = new TreeButtonsExercise(driver);
            String url = treeButtons.generateUrl(seed);
            driver.get(url);
            treeButtons.clickButtons();
            printExerciseResult(treeButtons);

            System.out.println("  \"test: EDITBOX\"");
            EditBoxExercise editBox = new EditBoxExercise(driver);
            url = editBox.generateUrl(seed);
            driver.get(url);
            editBox.writeText();
            printExerciseResult(editBox);

            System.out.println("  \"test: DROPDOWN LIST\"");

            DropdownListExercise dropdownList = new DropdownListExercise(driver);
            url = dropdownList.generateUrl(seed);
            driver.get(url);
            dropdownList.chooseOption();
            printExerciseResult(dropdownList);

            System.out.println("  \"test: RADIO BUTTONS\"");
            RadioButtonsExercise radioButtons = new RadioButtonsExercise(driver);
            url = radioButtons.generateUrl(seed);
            driver.get(url);
            radioButtons.selectButtons();
            printExerciseResult(radioButtons);
        } finally {
            driver.quit();
        }

        System.out.println("2.Certificate controller tests\n");
        CertificateController controller = new CertificateController();

        System.out.println("  test: Add new certificate");
        Certificate certificate = new Certificate(ORGANIZATION_TEST_VALUE, NAME_TEST_VALUE,
                PERIOD_TEST_VALUE, TRADE_TEST_VALUE);
        HttpResponse httpResponse = controller.addCertificate(certificate);
        printCertificateResult(httpResponse.getStatusLine().getStatusCode(), CODE_201);

        System.out.println("  test: Add incorrect certificate");
        Certificate incorrectCertificate = new Certificate();
        httpResponse = controller.addCertificate(incorrectCertificate);
        printCertificateResult(httpResponse.getStatusLine().getStatusCode(), CODE_500);

        System.out.println("  test: Find all certificates");
        CertificateResponse certificateResponse = controller.getAllCertificate();
        printCertificateResult(certificateResponse.getCode(), CODE_200);
        int existingId = certificateResponse.getCertificates().stream()
                .filter(f -> NAME_TEST_VALUE.equals(f.getName()))
                .findFirst().orElse(certificateResponse.getCertificates().get(0)).getId();

        System.out.println("  test: Find certificate by id as query params");
        certificateResponse = controller.getCertificateByIdInQuery(existingId);
        printCertificateResult(certificateResponse.getCode(), CODE_200);

        System.out.println("  test: Find certificate by id as Path params");
        certificateResponse = controller.getCertificateByIdInPath(existingId);
        printCertificateResult(certificateResponse.getCode(), CODE_200);

        System.out.println("  test: Change certificate");
        certificate.setName(NEW_NAME_TEST_VALUE_2);
        httpResponse = controller.changeCertificate(existingId, certificate);
        printCertificateResult(httpResponse.getStatusLine().getStatusCode(), CODE_200);

        System.out.println("  test: Delete certificate");
        httpResponse = controller.deleteCertificate(existingId);
        printCertificateResult(httpResponse.getStatusLine().getStatusCode(), CODE_200);

        System.out.println("  test: Find certificate by wrong id as query params");
        certificateResponse = controller.getCertificateByIdInQuery(existingId);
        printCertificateResult(certificateResponse.getCode(), CODE_404);

        System.out.println("  test: Find certificate by wrong id as Path params");
        certificateResponse = controller.getCertificateByIdInPath(existingId);
        printCertificateResult(certificateResponse.getCode(), CODE_404);

        System.out.println("  test: Delete nonexistent certificate");
        httpResponse = controller.deleteCertificate(existingId);
        printCertificateResult(httpResponse.getStatusLine().getStatusCode(), CODE_404);

        System.out.println("SUMMARY SUCCESS/FAILS : "  + GREEN + success + WHITE + "/" + RED + fail+ WHITE);



    }

    public static void printExerciseResult(Exercise exercise) {
        if (exercise.checkSolution()) {
            System.out.println("\tResult : " + GREEN + "SUCCESS" + WHITE);
            success++;
        }
        else {
            System.out.println("\tResult : " + RED + "FAIL" + WHITE);
            fail++;
        }
        System.out.println();
    }

    public static void printCertificateResult(int code , int excepted){
        if (excepted == code) {
            System.out.println("\tResult : " + GREEN + "SUCCESS" + WHITE);
            success++;
        }
        else {
            System.out.println("\tResult : " + RED + "FAIL" + WHITE);
            fail++;
        }
        System.out.println();
    }

    public static void init(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu");
        if (headless){
            driver = new ChromeDriver(options);
        } else {
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT, TimeUnit.SECONDS);
        seed = getGeneratedSeed();
    }
    private static String getGeneratedSeed() {
        driver.get(HOME_PAGE_URL);
        return driver.findElement(SEED_LOCATION).getText();
    }
}
