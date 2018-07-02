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
        driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
    }
    @Test
    public void tasktest() throws Exception {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver,15).withMessage("Element not found");
        WebElement boxClose = driver.findElement(By.xpath("//a[text()='Спасибо, я уже открыл']"));
        boxClose.click();
        driver.navigate().refresh();
        WebElement closeThirdWindow = driver.findElement(By.cssSelector("#new-requirements-digital-signature-modal > div.new-requirements__body > a.new-requirements__link"));
        closeThirdWindow.click();
        WebElement radioButton = driver.findElement(By.cssSelector("#dnn_ctr691_View_RadioButton9"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#new-requirements-digital-signature-modal")));
        radioButton.click();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        driver.close();
        driver.switchTo().window(tabs.get(1));
        WebElement fz223CheckBbox = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_chkPurchaseType_0"));
        fz223CheckBbox.click();
        WebElement commercialPurchase = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_chkPurchaseType_1"));
        commercialPurchase.click();
        WebElement startPrice = driver.findElement(By.id("BaseMainContent_MainContent_txtStartPrice_txtRangeFrom"));
        startPrice.click();
        startPrice.sendKeys("0");
        WebElement searchButton = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_btnSearch"));
        if (fz223CheckBbox.isSelected()&&commercialPurchase.isSelected()){
            searchButton.click();
        }
        WebElement nextPage = driver.findElement(By.cssSelector("#next_t_BaseMainContent_MainContent_jqgTrade_toppager"));
        nextPage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#next_t_BaseMainContent_MainContent_jqgTrade_toppager")));
        String price;
        String eisNumberValue;
        String priceValue;
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@id=\"sp_1_BaseMainContent_MainContent_jqgTrade_toppager\"]"))));
        WebElement pageCounter = driver.findElement(By.xpath("//span[@id=\"sp_1_BaseMainContent_MainContent_jqgTrade_toppager\"]"));
       String totalPages = pageCounter.getText().toString();
        totalPages = totalPages.replaceAll(" ","").trim();
       Integer totalPagesValue = Integer.valueOf(totalPages);
        WebElement currentPage = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_jqgTrade_toppager_center > table > tbody > tr > td:nth-child(4) > input"));
        String currentPageAttribute = currentPage.getAttribute("value");
        Integer currentPageValue = Integer.valueOf(currentPageAttribute.trim());
        List<WebElement> eisNumberColumn;
        List<WebElement> startPriceColumn;
        WebElement EisCell;
        WebElement PriceCell;
        String compareCurrentPageValue;
      while (currentPageValue<totalPagesValue+1){
          eisNumberColumn = driver.findElements(By.xpath("//td[@aria-describedby='BaseMainContent_MainContent_jqgTrade_OosNumber']"));
              startPriceColumn = driver.findElements(By.xpath("//td[@aria-describedby='BaseMainContent_MainContent_jqgTrade_StartPrice']"));
              for (int i = 0; i < eisNumberColumn.size(); i++) {
                  System.out.println(i);
                  EisCell = eisNumberColumn.get(i);
                  eisNumberValue = EisCell.getText();
                  System.out.println(eisNumberValue);
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
                compareCurrentPageValue = String.valueOf(currentPageValue+1);
              if (currentPageValue!=totalPagesValue){
                  nextPage.click();
              wait.until(ExpectedConditions.attributeToBe(currentPage,"value",compareCurrentPageValue));
                  }
                  else{break;}
              eisNumberColumn = null;
              startPriceColumn = null;
          currentPageAttribute = currentPage.getAttribute("value");
          currentPageValue = Integer.valueOf(currentPageAttribute.trim());
          }
        WebElement canceledButton = driver.findElement(By.cssSelector("#lotStateTabs > ul > li:nth-child(11) > a"));
        canceledButton.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"sp_1_BaseMainContent_MainContent_jqgTrade_toppager\"]"))));
        WebElement canceledPageCount = driver.findElement(By.xpath("//*[@id=\"sp_1_BaseMainContent_MainContent_jqgTrade_toppager\"]"));
        WebElement canceledCurrentPage = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_jqgTrade_toppager_center > table > tbody > tr > td:nth-child(4) > input"));
        WebElement canceledNextPage = driver.findElement(By.cssSelector("#next_t_BaseMainContent_MainContent_jqgTrade_toppager"));
        totalPages = null;
        totalPages = canceledPageCount.getText().toString();
        totalPages = totalPages.replaceAll(" ","").trim();
        totalPagesValue = Integer.valueOf(totalPages);
        currentPageAttribute = canceledCurrentPage.getAttribute("value");
        currentPageValue = Integer.valueOf(currentPageAttribute.trim());
        System.out.println(currentPageValue);
        int canceledCounter=0;
        while (currentPageValue<totalPagesValue+1){
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
                        canceledSumTotal = Double.valueOf(priceValue) + canceledSumTotal;
                        canceledCounter = canceledCounter + 1;
                    }
                }
                compareCurrentPageValue = String.valueOf(currentPageValue+1);
            if (currentPageValue!=totalPagesValue){
                canceledNextPage.click();
                wait.until(ExpectedConditions.attributeToBe(canceledCurrentPage,"value",compareCurrentPageValue));
                 }
                else {break;}
                eisNumberColumn = null;
                startPriceColumn = null;
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
        driver.quit();
    }
}
