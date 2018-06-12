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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author duncan
 */
public class CreatePageFFTest {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\duncan\\Programme\\chromedriver_win32\\chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\duncan\\Programme\\geckodriver\\geckodriver-v0.19.0-win64\\geckodriver.exe");
        System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox New\\firefox.exe");

    }

    @Before
    public void setUp() throws Exception {

         FirefoxOptions options = new FirefoxOptions();
        options.addPreference("security.sandbox.content.level", 5);
        //options.addArguments("--headless");
        driver = new FirefoxDriver(options);
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
        
        for(int i = 0; i < 10; i++) {
           // mouseAction.moveToElement(newMenu).perform();
        }
        

        
        wait.until(driver -> {
           
            try {
                 mouseAction.moveToElement(newMenu).perform();
             System.out.println("....");
                return driver.findElement(By.cssSelector("#wp-admin-bar-new-post")).isDisplayed();
            } catch (NoSuchElementException e) {
                return false;
            }
        });
        mouseAction.moveToElement(newPost).click().perform();
        
        
        
        mouseAction.moveToElement(newPost).click().perform();
        System.out.println("something to look at");
        
        WebElement title = driver.findElement(By.id("title"));
        wait.until(ExpectedConditions.visibilityOf(title));
        title.click();
        title.sendKeys("firefox");
        //wait.until(ExpectedConditions.textToBe(By.id("title"), "firefox"));
        driver.findElement(By.id("publish")).click();

        WebElement profile = driver.findElement(By.cssSelector("#wp-admin-bar-my-account"));
        WebElement logout = driver.findElement(By.cssSelector("#wp-admin-bar-logout"));
        System.out.println(logout.getText());
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
