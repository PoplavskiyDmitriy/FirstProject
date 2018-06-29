package ru.mail.dimon1985;

import com.thoughtworks.selenium.Selenium;
import org.apache.bcel.generic.Select;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirstTest {
    private static WebDriver driver;
    private static int counter = 0;
    private static Double sumTotal=0.0;
    private static Double canceledSumTotal=0.0;
    private static double total;

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
        final Wait<WebDriver> wait = new WebDriverWait(driver,10).withMessage("Element not found");
        WebElement boxClose = driver.findElement(By.xpath("//a[text()='Спасибо, я уже открыл']"));
        boxClose.click();
        driver.navigate().refresh();
        WebElement closeThirdWindow = driver.findElement(By.cssSelector("#new-requirements-digital-signature-modal > div.new-requirements__body > a.new-requirements__link"));
        closeThirdWindow.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("dnn_ctr691_View_RadioButton9"))));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement radioButton = driver.findElement(By.cssSelector("#dnn_ctr691_View_RadioButton9"));
        radioButton.click();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        driver.close();
        driver.switchTo().window(tabs.get(1));
        WebElement fz223ChecBbox = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_chkPurchaseType_0"));
        fz223ChecBbox.sendKeys(Keys.SPACE);
        WebElement commercialPurchase = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_chkPurchaseType_1"));
        commercialPurchase.click();
        WebElement startPrice = driver.findElement(By.id("BaseMainContent_MainContent_txtStartPrice_txtRangeFrom"));
        startPrice.click();
        startPrice.sendKeys("0");
        WebElement searchButton = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_btnSearch"));
        if (fz223ChecBbox.isSelected()&&commercialPurchase.isSelected()){
            searchButton.click();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement nextPage = driver.findElement(By.cssSelector("#next_t_BaseMainContent_MainContent_jqgTrade_toppager"));
        String price;
        String eisNumberValue;
        String priceValue;
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@id=\"sp_1_BaseMainContent_MainContent_jqgTrade_toppager\"]"))));
        WebElement pageCounter = driver.findElement(By.xpath("//span[@id=\"sp_1_BaseMainContent_MainContent_jqgTrade_toppager\"]"));
       String totalPages = pageCounter.getText().toString();
        totalPages = totalPages.replaceAll(" ","").trim();
       Integer totalPagesValue = Integer.valueOf(totalPages);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#BaseMainContent_MainContent_jqgTrade_toppager_center > table > tbody > tr > td:nth-child(4) > input"))));
        WebElement currentPage = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_jqgTrade_toppager_center > table > tbody > tr > td:nth-child(4) > input"));
        String currentPageAttribute = currentPage.getAttribute("value");
        Integer currentPageValue = Integer.valueOf(currentPageAttribute.trim());
        List<WebElement> eisNumberColumn;
        List<WebElement> startPriceColumn;
        WebElement EisCell;
        WebElement PriceCell;
      while (currentPageValue<totalPagesValue){
          //if (currentPageValue<totalPagesValue) {
              eisNumberColumn = driver.findElements(By.xpath("//td[@aria-describedby='BaseMainContent_MainContent_jqgTrade_OosNumber']"));
              startPriceColumn = driver.findElements(By.xpath("//td[@aria-describedby='BaseMainContent_MainContent_jqgTrade_StartPrice']"));
              for (int i = 0; i < eisNumberColumn.size(); i++) {
                  EisCell = eisNumberColumn.get(i);
                  eisNumberValue = EisCell.getText();
                  if (!eisNumberValue.trim().equals("")) {
                      PriceCell = startPriceColumn.get(i);
                      price = PriceCell.getText();
                      priceValue = price.substring(0, price.length() - 4);
                      priceValue = priceValue.replaceAll(" ", "");
                      priceValue = priceValue.replaceAll(",", ".");
                      sumTotal = Double.valueOf(priceValue) + sumTotal;
                      counter = counter + 1;
                  }
              }
              try {
                  TimeUnit.SECONDS.sleep(2);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              nextPage.click();
              try {
                  TimeUnit.SECONDS.sleep(2);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }

              eisNumberColumn = null;
              startPriceColumn = null;
          //}
          //else { break;}
          currentPageAttribute = currentPage.getAttribute("value");
          currentPageValue = Integer.valueOf(currentPageAttribute.trim());
          }
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#lotStateTabs > ul > li:nth-child(10) > a"))));
        WebElement canceledButton = driver.findElement(By.cssSelector("#lotStateTabs > ul > li:nth-child(10) > a"));
        canceledButton.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"sp_1_BaseMainContent_MainContent_jqgTrade_toppager\"]"))));
        WebElement canceledPageCount = driver.findElement(By.xpath("//*[@id=\"sp_1_BaseMainContent_MainContent_jqgTrade_toppager\"]"));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#BaseMainContent_MainContent_jqgTrade_toppager_center > table > tbody > tr > td:nth-child(4) > input"))));
        WebElement canceledCurrentPage = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_jqgTrade_toppager_center > table > tbody > tr > td:nth-child(4) > input"));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#next_t_BaseMainContent_MainContent_jqgTrade_toppager"))));
        WebElement canceledNextPage = driver.findElement(By.cssSelector("#next_t_BaseMainContent_MainContent_jqgTrade_toppager"));
        totalPages = null;
        totalPages = canceledPageCount.getText().toString();
        totalPages = totalPages.replaceAll(" ","").trim();
        totalPagesValue = Integer.valueOf(totalPages);
        currentPageAttribute = canceledCurrentPage.getAttribute("value");
        currentPageValue = Integer.valueOf(currentPageAttribute.trim());
        int canceledCounter=0;
        while (currentPageValue<totalPagesValue){
            //if (currentPageValue<totalPagesValue) {
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//td[@aria-describedby='BaseMainContent_MainContent_jqgTrade_OosNumber']"))));
                eisNumberColumn = driver.findElements(By.xpath("//td[@aria-describedby='BaseMainContent_MainContent_jqgTrade_OosNumber']"));
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//td[@aria-describedby='BaseMainContent_MainContent_jqgTrade_StartPrice']"))));
                startPriceColumn = driver.findElements(By.xpath("//td[@aria-describedby='BaseMainContent_MainContent_jqgTrade_StartPrice']"));
                for (int i = 0; i < eisNumberColumn.size(); i++) {
                    EisCell = eisNumberColumn.get(i);
                    eisNumberValue = EisCell.getText();
                    if (!eisNumberValue.trim().equals("")) {
                        PriceCell = startPriceColumn.get(i);
                        price = PriceCell.getText();
                        priceValue = price.substring(0, price.length() - 4);
                        priceValue = priceValue.replaceAll(" ", "");
                        priceValue = priceValue.replaceAll(",", ".");
                        canceledSumTotal = Double.valueOf(priceValue) + canceledSumTotal;
                        canceledCounter = canceledCounter + 1;
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                canceledNextPage.click();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                eisNumberColumn = null;
                startPriceColumn = null;
            //}
            //else { break;}
            currentPageAttribute = canceledCurrentPage.getAttribute("value");
            currentPageValue = Integer.valueOf(currentPageAttribute.trim());
        }
        System.out.println("Total number of lots: "+counter);
        System.out.print("Total start price: ");
        System.out.printf("%.2f",sumTotal);
        System.out.println();
        System.out.print("Total start price of canceled lots: ");
        System.out.printf("%.2f",canceledSumTotal);
        total = sumTotal-canceledSumTotal;
        System.out.println();
        System.out.print("Total: ");
        System.out.printf("%.2f",total);

    }
    @AfterClass
    public static void write () throws Exception{
        FileWriter report = new FileWriter("F://report.txt");
        report.write("Total number of lots: "+counter+"\n");
        report.write("Total start price: ");
        report.write(String.format("%(.2f",sumTotal));
        report.write("\nTotal start price of canceled lots: ");
        report.write(String.format("%(.2f",canceledSumTotal));
        report.write("\nTotal: ");
        report.write(String.format("%(.2f",total));
        report.close();
        System.out.println("Recorded");
        //driver.quit();

    }
}
