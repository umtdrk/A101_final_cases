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

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class HepsiburadaUyeGirisi {
    private WebDriver driver;
    JavascriptExecutor js;
    WebDriverWait wait;
    @Before
    //Driver kurulumu ve gerekli ayarlamalar yapılır.
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
    public void sepeteUrunekle() throws InterruptedException {
        //Üye girişi yapılır.
        driver.findElement(By.id("myAccount")).click();
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("txtUserName")).sendKeys("umitdrk33@gmail.com");
        driver.findElement(By.id("btnLogin")).click();
        driver.findElement(By.id("txtPassword")).sendKeys("Test123456");
        driver.findElement(By.id("btnEmailSelect")).click();
        //istenilen ürün aranır.
        driver.findElement(By.className("desktopOldAutosuggestTheme-UyU36RyhCTcuRs_sXL9b")).click();
        driver.findElement(By.className("desktopOldAutosuggestTheme-UyU36RyhCTcuRs_sXL9b")).sendKeys("sleepy bebek bezi 5 numara");
        driver.findElement(By.className("SearchBoxOld-cHxjyU99nxdIaAbGyX7F")).click();
        js.executeScript("window.scrollTo(0,100)");
        Thread.sleep(500);

        //ilk ürün secilir.
        driver.findElement(By.xpath("//*[@id='i0']/div/a/div[1]/div[1]/div[2]")).click();
        //yeni sekmeye gecilir.
        for (String handle : driver.getWindowHandles())
        { driver.switchTo().window(handle); }
        js.executeScript("window.scrollTo(0,300)");
        Thread.sleep(500);
        //ürün sepete eklenir.
        driver.findElement(By.id("addToCart")).click();
        Thread.sleep(1000);

        driver.findElement(By.className("checkoutui-Modal-iHhyy79iR28NvF33vKJb")).click();
        js.executeScript("window.scrollTo(0,500)");
        //2. urun sepete eklenir ve sepete gidilir.
        driver.findElement(By.cssSelector("tr:nth-child(1) .add-to-basket")).click();
        WebElement sepeteGitButton = driver.findElement(By.xpath("//button[@type='button'] [contains(text(),'Sepete git')]"));
        wait.until(ExpectedConditions.elementToBeClickable(sepeteGitButton)).click();
        // sepetteki ürünlerin dogru oldugu dogrulanır.
        assertThat(driver.findElement(By.xpath("(//div[@class='product_name_3Lh3t'])[1]")).getText(), containsString("Bebek Bezi 5 Numara"));
        js.executeScript("window.scrollTo(0,250)");
        assertThat(driver.findElement(By.xpath("(//div[@class='product_name_3Lh3t'])[2]")).getText(), containsString("Bebek Bezi 5 Numara"));
        //sepeti boşaltılır.
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//a[@aria-label='Ürünü Kaldır'])[1]")).click();
        try {driver.findElement(By.cssSelector("button[kind='secondary']")).click();
        }catch (Exception e){}
        Thread.sleep(500);
        driver.findElement(By.xpath("//a[@aria-label='Ürünü Kaldır']")).click();
    }
    @After
    //driver sonlandırılır.
    public void tearDown() {
        driver.quit();
    }
}
