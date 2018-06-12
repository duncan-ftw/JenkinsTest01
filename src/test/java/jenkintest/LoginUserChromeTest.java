package jenkintest;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginUserChromeTest {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\duncan\\Programme\\chromedriver_win32\\chromedriver.exe");

        driver = new ChromeDriver();
        baseUrl = "http://127.0.0.1/";
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
    }

    @Test
    public void testLoginUserXPath() throws Exception {
        driver.get(baseUrl + "/wordpress/");
        driver.findElement(By.cssSelector("#meta-2 > ul > li > a")).click();
        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys("user");
        driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys("password");
        driver.findElement(By.id("wp-submit")).click();

        Actions mouseAction = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        
        WebElement profile = driver.findElement(By.cssSelector("#wp-admin-bar-my-account > a.ab-item"));
        WebElement logout = driver.findElement(By.cssSelector("#wp-admin-bar-logout > a.ab-item"));
        mouseAction.moveToElement(profile).perform();
        wait.until(ExpectedConditions.visibilityOf(logout));
        mouseAction.moveToElement(logout).click().perform();
        
        assertEquals("You are now logged out.", driver.findElement(By.cssSelector("p.message")).getText());
        Thread.sleep(3000); // allows to visually inspect the website
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
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
