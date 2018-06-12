package jenkintest;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginUserTestFF59Test {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {

        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox New\\firefox.exe");

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\duncan\\Programme\\chromedriver_win32\\chromedriver.exe");

        // UnsupportedCommandException
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.15.0-win64\\geckodriver.exe");
        // not working
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.16.1-win64\\geckodriver.exe");
        // not working
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.17.0-win64\\geckodriver.exe");
        


        // Move Action is sensible use until
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.18.0-win64\\geckodriver.exe");
        // Move Action is sensible use until
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.19.0-win64\\geckodriver.exe");
        // Move Action is sensible use until
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.19.1-win64\\geckodriver.exe");
        // Move Action is sensible use until
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.20.0-win64\\geckodriver.exe");
        // Move Action is sensible use until
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.20.1-win64\\geckodriver.exe");
        //driver = new ChromeDriver();
        FirefoxOptions options = new FirefoxOptions();
        //FirefoxProfile profile = options.getProfile();
        options.addPreference("security.sandbox.content.level", 5);
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
        baseUrl = "http://127.0.0.1/";
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testLoginUser() throws Exception {

        driver.get(baseUrl + "/wordpress/");
        driver.findElement(By.linkText("Log in")).click();
        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys("user");
        driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys("password");
        driver.findElement(By.id("wp-submit")).click();

        //waitForLoad(driver);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(By.cssSelector("#wp-admin-bar-my-account > a.ab-item > span"), "user"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#wp-admin-bar-my-account > a.ab-item")));
        Actions act = new Actions(driver);

        WebElement profile = driver.findElement(By.cssSelector("#wp-admin-bar-my-account > a.ab-item"));

        WebElement logout = driver.findElement(By.cssSelector("#wp-admin-bar-logout > a.ab-item"));
        wait.until(ExpectedConditions.visibilityOf(profile));
        wait.until(driver -> {
            act.moveToElement(profile).perform();
            System.out.println("....");
            try {
                return driver.findElement(By.cssSelector("#wp-admin-bar-logout > a.ab-item")).isDisplayed();
            } catch (NoSuchElementException e) {
                return false;
            }
        });
        act.moveToElement(logout).click().perform();

        By logOutMessage = By.cssSelector("p.message");

        wait.until(ExpectedConditions.presenceOfElementLocated(logOutMessage));
        assertEquals("You are now logged out.", driver.findElement(logOutMessage).getText());
        Thread.sleep(3000); // allows to visually inspect the website

    }

    public void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                System.out.println("not loaded.....");
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);

        System.out.println("loaded.....");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        /*String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }*/
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
