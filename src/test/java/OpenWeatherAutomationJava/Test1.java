package OpenWeatherAutomationJava;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class Test1
{
    private WebDriver webDriver;

    @BeforeTest
    public void beforeTest()
    {
        webDriver = new FirefoxDriver();
    }

    @AfterTest
    public void afterTest()
    {
        webDriver.quit();
    }

    @Test
    public void test1()
    {
        webDriver.get("https://openweathermap.org");
        String title = webDriver.getTitle();				 
		Assert.assertTrue(title.contains("OpenWeather"));
    }
}
