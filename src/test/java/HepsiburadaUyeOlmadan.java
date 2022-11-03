import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class HepsiburadaUyeOlmadan {
  private WebDriver driver;
  JavascriptExecutor js;
  WebDriverWait wait;
  @Before
  //driver kurulumu ve ayarlamaları yapılır.
  public void setUp() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.get("https://www.hepsiburada.com/");
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    wait = new WebDriverWait(driver, 10);
    js = (JavascriptExecutor) driver;
  }
  @Test
  public void sepeteUrunEkle() throws InterruptedException {
    //istenilen ürün araması yapılır.
    driver.findElement(By.className("desktopOldAutosuggestTheme-UyU36RyhCTcuRs_sXL9b")).click();
    driver.findElement(By.className("desktopOldAutosuggestTheme-UyU36RyhCTcuRs_sXL9b")).sendKeys("sleepy bebek bezi 5 numara");
    driver.findElement(By.className("SearchBoxOld-cHxjyU99nxdIaAbGyX7F")).click();
    Thread.sleep(500);
    js.executeScript("window.scrollTo(0,100)");
    Thread.sleep(500);
    //ilk ürün seçilir.
    driver.findElement(By.xpath("//*[@id='i0']/div/a/div[1]/div[1]/div[2]")).click();
    //yeni acilan sekmeye geçilir.
    for (String handle : driver.getWindowHandles())
    { driver.switchTo().window(handle); }
    js.executeScript("window.scrollTo(0,300)");
    Thread.sleep(500);
    //ürün sepete eklenir.
    driver.findElement(By.id("addToCart")).click();
    Thread.sleep(1000);

    driver.findElement(By.className("checkoutui-Modal-iHhyy79iR28NvF33vKJb")).click();
    js.executeScript("window.scrollTo(0,500)");
    //2. ürün sepete eklenir ve sepete gidilir.
    driver.findElement(By.cssSelector("tr:nth-child(1) .add-to-basket")).click();

    WebElement sepeteGitButton = driver.findElement(By.xpath("//button[@type='button'] [contains(text(),'Sepete git')]"));
    wait.until(ExpectedConditions.elementToBeClickable(sepeteGitButton)).click();
    //sepetteki ürünlerin doğru olduğu doğrulanır.
    assertThat(driver.findElement(By.xpath("(//div[@class='product_name_3Lh3t'])[1]")).getText(), containsString("Bebek Bezi 5 Numara"));
    js.executeScript("window.scrollTo(0,250)");
    assertThat(driver.findElement(By.xpath("(//div[@class='product_name_3Lh3t'])[2]")).getText(), containsString("Bebek Bezi 5 Numara"));
    Thread.sleep(1000);
  }
  @After
  //driver sonlandırılır.
  public void tearDown() {
    driver.quit();
  }
}
