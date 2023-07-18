import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class CheckTicketsToMaltaTest {
    WebDriver webDriver;

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @Test
    public void ticketsCheck() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

        By origin = By.xpath("//*[@id='origin']");
        By destination = By.xpath("//*[@id='destination']");
        By searchTicketsButton = By.xpath("//button[@data-test-id='form-submit']");
        By ticketsQuantity = By.xpath("//p[text()='Нет прямых рейсов']");
        webDriver.get("https://www.aviasales.by/");

        webDriver.findElement(origin).sendKeys("Варшава");
        webDriver.findElement(destination).sendKeys("Мальта");
        webDriver.findElement(searchTicketsButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(ticketsQuantity));

        String availableTickets = webDriver.findElement(ticketsQuantity).getText();
        Assert.assertEquals(availableTickets, "Нет прямых рейсов");
    }

    @AfterTest
    public void teardown() {
        webDriver.quit();
    }
}
