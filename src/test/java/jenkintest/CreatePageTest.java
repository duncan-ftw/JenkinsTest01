/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jenkintest;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author duncan
 */
public class CreatePageTest {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\duncan\\Programme\\chromedriver_win32\\chromedriver.exe");
    }

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\duncan\\Programme\\chromedriver_win32\\chromedriver.exe");

        driver = new ChromeDriver();
        baseUrl = "http://127.0.0.1/";
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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

        WebElement newMenu = driver.findElement(By.cssSelector("#wp-admin-bar-new-content"));
        WebElement newPost = driver.findElement(By.cssSelector("#wp-admin-bar-new-post"));

        mouseAction.moveToElement(newMenu).perform();
        wait.until(ExpectedConditions.visibilityOf(newPost));
        mouseAction.moveToElement(newPost).click().perform();

        driver.findElement(By.id("title")).sendKeys("testPost");
        driver.findElement(By.id("publish")).click();

        WebElement profile = driver.findElement(By.cssSelector("#wp-admin-bar-my-account"));
        WebElement logout = driver.findElement(By.cssSelector("#wp-admin-bar-logout"));
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

}
