package ru.mail.dimon1985;

import com.thoughtworks.selenium.Selenium;
import org.apache.bcel.generic.Select;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirstTest {
    private static WebDriver driver;

    @BeforeClass
    public static void setup(){
        System.setProperty("webdriver.chrome.driver", "F:\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.rts-tender.ru/auctionsearch");

    }
    @Test
    public void tasktest() {
        WebElement boxClose = driver.findElement(By.xpath("//a[text()='Спасибо, я уже открыл']"));
        boxClose.click();
        driver.navigate().refresh();
        WebElement fz_223_radiobtn = driver.findElement(By.id("dnn_ctr691_View_RadioButton9"));
        fz_223_radiobtn.click();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        driver.close();
        driver.switchTo().window(tabs.get(1));
        WebElement fz_223_checkbox = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_chkPurchaseType_0"));
        fz_223_checkbox.sendKeys(Keys.SPACE);
        WebElement commercial_purchase = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_chkPurchaseType_1"));
        commercial_purchase.click();
        WebElement startPrice = driver.findElement(By.id("BaseMainContent_MainContent_txtStartPrice_txtRangeFrom"));
        startPrice.click();
        startPrice.sendKeys("0");
        WebElement search_button = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_btnSearch"));
        if (fz_223_checkbox.isSelected()&&commercial_purchase.isSelected()){
            search_button.click();
        }
        int counter = 0;
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(driver.findElement(By.cssSelector("#BaseMainContent_MainContent_jqgTrade_toppager_center > table > tbody > tr > td:nth-child(8) > select")));
        select.selectByValue("100");
        WebElement next = driver.findElement(By.cssSelector("#next_t_BaseMainContent_MainContent_jqgTrade_toppager"));
        WebElement previous = driver.findElement(By.cssSelector("#prev_t_BaseMainContent_MainContent_jqgTrade_toppager"));
        //next.click();
        //previous.click();
        WebElement pages = driver.findElement(By.xpath("//*[@id=\'sp_1_BaseMainContent_MainContent_jqgTrade_toppager\']"));
        String s = pages.getText();
        System.out.println(s);
        String s2;
        List<WebElement> rows_table = driver.findElements(By.xpath("//table[@id=\"BaseMainContent_MainContent_jqgTrade\"]/tbody/tr['+k+']"));
        System.out.println(rows_table.size());
        WebElement thirdCell = driver.findElement(By.xpath("//table[@id=\"BaseMainContent_MainContent_jqgTrade\"]/tbody/tr[3]/td[6]"));
        s2 = thirdCell.getText();
        System.out.println(s2);

        /*WebElement thirdCell = driver.findElement(By.xpath("//table[@id=\"BaseMainContent_MainContent_jqgTrade\"]/tbody/tr[i]/td[6]"));
        String s1 = thirdCell.getText();
        System.out.println(s1);*/
        // WebElement number =table.findElement(By.xpath("//*[@id=\"1106898\"]/td[6]"));


    }
}
