package OpenWeatherAutomationJava;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
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
        //System.setProperty("webdriver.firefox.marionette","C:\\Windows\\System32\\geckodriver.exe");
        
        
        FirefoxProfile fProfile=new FirefoxProfile();
        fProfile.setPreference("geo.prompt.testing", true);
        fProfile.setPreference("geo.enabled", true);
        fProfile.setPreference("geo.prompt.testing.allow", true);
        fProfile.setPreference("geo.provider.use_corelocation", true);

        DesiredCapabilities dCapabilities=new DesiredCapabilities();
        dCapabilities.setCapability(FirefoxDriver.PROFILE,fProfile);
        
        FirefoxOptions fopts=new FirefoxOptions();
        fopts.merge(dCapabilities);

        webDriver = new FirefoxDriver(fopts);
        
    }

    @AfterTest
    public void afterTest()
    {
        webDriver.quit();
    }

    @Parameters({"baseuri"})
    @Test
    public void verifyTitle(String baseuri)
    {
        webDriver.get(baseuri);
        System.out.println(baseuri);
        String title = webDriver.getTitle();				 
		Assert.assertTrue(title.contains("The Internet"));
    }

    @Parameters({"baseuri"})
    @Test
    public void abtesting(String baseuri)
    {
        webDriver.get(baseuri);
        
        WebElement abLink=webDriver.findElement(By.linkText("A/B Testing"));	
        abLink.click();
        WebElement abHeader=webDriver.findElement(By.tagName("h3"));
        String headertext=abHeader.getText();		 
		Assert.assertTrue(headertext.equalsIgnoreCase("No A/B Test"));
    }

    @Parameters({"baseuri"})
    @Test
    public void getImagesCount(String baseuri)
    {
        webDriver.get(baseuri);
        
        WebElement bimglink=webDriver.findElement(By.linkText("Broken Images"));	
        bimglink.click();
        
        List<WebElement> images=webDriver.findElements(By.tagName("img"));
        
        Assert.assertEquals(4,images.size());
    }

    @Parameters({"baseuri"})
    @Test
    public void challengeDOM(String baseuri)
    {
        webDriver.get(baseuri);
        WebElement cDOMlink=webDriver.findElement(By.linkText("Challenging DOM"));	
        cDOMlink.click();

        WebElement normalbutton=webDriver.findElement(By.className("button"));

        Assert.assertTrue(normalbutton.isDisplayed());


    }

    @Parameters({"baseuri"})
    @Test
    public void checkauth(String baseuri) throws AWTException, InterruptedException, IOException
    {
        webDriver.get(baseuri);
        WebElement basiclink=webDriver.findElement(By.linkText("Basic Auth"));	
        basiclink.click();

        Robot rb=new Robot();
        Alert basic=webDriver.switchTo().alert();				 
        basic.sendKeys("admin"+ Keys.TAB + "admin");
        //Thread.sleep(2000);
        //basic.sendKeys(Keys.TAB.toString());
        /*rb.delay(1200);
        rb.keyPress(KeyEvent.VK_TAB);
        basic=webDriver.switchTo().alert();
        basic.sendKeys("admin");*/
        
        basic.accept();
        Thread.sleep(2000);
        File screenshotFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile,
                new File("C:\\scratch\\shots\\screenshot"+Math.random()+".png"));

        WebElement ptext=webDriver.findElement(By.tagName("p"));
        String text=ptext.getText();
        Assert.assertTrue(text.contains("Congratulations"), "Text did not contain Congratulations");
    }
}
