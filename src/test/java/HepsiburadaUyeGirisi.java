import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HepsiburadaUyeGirisi {
    private WebDriver driver;
    JavascriptExecutor js;
    WebDriverWait wait;
    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        //avoid-bot-detection
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        driver = new ChromeDriver(options);

        driver.get("https://www.hepsiburada.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void hepsiburadayegirii() throws InterruptedException {
//        Actions action = new Actions(driver);
//        action.moveToElement(driver.findElement(By.id("myAccount")));
        driver.findElement(By.id("myAccount")).click();
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("txtUserName")).sendKeys("umitdrk33@gmail.com");
        driver.findElement(By.id("btnLogin")).click();
        driver.findElement(By.id("txtPassword")).sendKeys("Test123456");
        driver.findElement(By.id("btnEmailSelect")).click();
        driver.findElement(By.className("desktopOldAutosuggestTheme-UyU36RyhCTcuRs_sXL9b")).click();
        driver.findElement(By.className("desktopOldAutosuggestTheme-UyU36RyhCTcuRs_sXL9b")).sendKeys("sleepy bebek bezi 5 numara");
        driver.findElement(By.className("SearchBoxOld-cHxjyU99nxdIaAbGyX7F")).click();

        js.executeScript("window.scrollTo(0,100)");
        Thread.sleep(500);

        driver.findElement(By.xpath("//*[@id='i0']/div/a/div[1]/div[1]/div[2]")).click();
        for (String handle : driver.getWindowHandles())
        { driver.switchTo().window(handle); }
        js.executeScript("window.scrollTo(0,300)");
        Thread.sleep(500);

        driver.findElement(By.id("addToCart")).click();
        Thread.sleep(1000);

        driver.findElement(By.className("checkoutui-Modal-2iZXl")).click();
        js.executeScript("window.scrollTo(0,500)");

        driver.findElement(By.cssSelector("tr:nth-child(1) .add-to-basket")).click();

        WebElement sepeteGitButton = driver.findElement(By.xpath("//button[@type='button'] [contains(text(),'Sepete git')]"));
        wait.until(ExpectedConditions.elementToBeClickable(sepeteGitButton)).click();

        assertThat(driver.findElement(By.linkText("Sleepy Extra Günlük Aktivite Ekonomik Paket Bebek Bezi 5 Numara Junior 40 Adet")).getText(), is("Sleepy Extra Günlük Aktivite Ekonomik Paket Bebek Bezi 5 Numara Junior 40 Adet"));
        js.executeScript("window.scrollTo(0,250)");
        assertThat(driver.findElement(By.linkText("Sleepy Extra Günlük Aktivite Ekonomik Paket Bebek Bezi 5 Numara Junior 40 Adet")).getText(), is("Sleepy Extra Günlük Aktivite Ekonomik Paket Bebek Bezi 5 Numara Junior 40 Adet"));
        //sepeti boşalt
//        driver.findElement(By.xpath("(//a[@class='product_delete_1zR-0'])[1]")).click();
//        driver.findElement(By.className("iDSyON ")).click();
//        Thread.sleep(500);
//        driver.findElement(By.xpath("(//a[@class='product_delete_1zR-0'])")).click();
    }
    @After
    public void tearDown() {
        //driver.quit();
    }
}
