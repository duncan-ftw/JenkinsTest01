package jenkintest;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginUserTestFF52Test {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox New\\firefox.exe");
        
        // Move Action not working
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.15.0-win64\\geckodriver.exe");

        // Working
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.16.1-win64\\geckodriver.exe");
        // Move Action not working
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.17.0-win64\\geckodriver.exe");
        // can't quit firefox
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.18.0-win64\\geckodriver.exe");
        // Nothing working
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.19.0-win64\\geckodriver.exe");
        // Nothing working
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.19.1-win64\\geckodriver.exe");
        // Nothing working
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.20.0-win64\\geckodriver.exe");
        // Nothing working
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.20.1-win64\\geckodriver.exe");
        //driver = new ChromeDriver();
        
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
        
        //driver = new FirefoxDriver();
        baseUrl = "http://127.0.0.1/";
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Ignore
    @Test
    public void testLoginUser() throws Exception {

        driver.get(baseUrl + "/wordpress/");
        driver.findElement(By.linkText("Log in")).click();
        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys("user");
        driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys("password");
        driver.findElement(By.id("wp-submit")).click();

        // WebDriverWait allows us to wait for certain Conditions, like a text to be present or 
        // an element to be visible
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Actions are used to perform mouse or keyboard actions, like moving and click with the mouse
        // we need this one for hoovering over elements
        Actions act = new Actions(driver);

        // WebElement represents an Element of the Webpage
        WebElement profile = driver.findElement(By.cssSelector("#wp-admin-bar-my-account > a.ab-item"));
        WebElement logout = driver.findElement(By.cssSelector("#wp-admin-bar-logout > a.ab-item"));

        
        wait.until(ExpectedConditions.textToBe(By.cssSelector("#wp-admin-bar-my-account > a.ab-item > span"), "user"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#wp-admin-bar-my-account > a.ab-item")));
        wait.until(ExpectedConditions.visibilityOf(profile));

        // wait.until gets a Function with return value boolean and a parameter WebDriver
        // the action is performed as long as defined in the creation of the wait WebDriverWait object --> 10s
        // the moveToElement is sort of bugy and has to be performed sometimes more than once ... at least in firefox
        wait.until(driver -> {
            act.moveToElement(profile).perform();
            try {
                return driver.findElement(By.cssSelector("#wp-admin-bar-logout > a.ab-item")).isDisplayed();
            } catch (NoSuchElementException e) {
                return false;
            }
        });
        act.moveToElement(logout).click().perform();

        // wait for the p.message to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.message")));
        assertEquals("You are now logged out.", driver.findElement(By.cssSelector("p.message")).getText());
        // allows to visually inspect the website
        Thread.sleep(3000);

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
