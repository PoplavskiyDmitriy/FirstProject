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

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirstTest {
    private static WebDriver driver;
    private static int counter = 0;
    private static Double whole_amount=0.0;
    private static Double whole_amount_abolished=0.0;
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
        WebElement next = driver.findElement(By.cssSelector("#next_t_BaseMainContent_MainContent_jqgTrade_toppager"));
        String price;
        String eis;
        String price_value;
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement page_count = driver.findElement(By.xpath("//span[@id=\"sp_1_BaseMainContent_MainContent_jqgTrade_toppager\"]"));
       String pages1 = page_count.getText().toString();
        pages1 = pages1.replaceAll(" ","").trim();
       Integer k = Integer.valueOf(pages1);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement current_page = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_jqgTrade_toppager_center > table > tbody > tr > td:nth-child(4) > input"));
        String page_current = current_page.getAttribute("value");
        Integer l = Integer.valueOf(page_current.trim());
        List<WebElement> eis_number;
        List<WebElement> start_price;
        WebElement cell;
        WebElement cell_price;
      while (true){
          if (l<k) {
              eis_number = driver.findElements(By.xpath("//td[@aria-describedby='BaseMainContent_MainContent_jqgTrade_OosNumber']"));
              start_price = driver.findElements(By.xpath("//td[@aria-describedby='BaseMainContent_MainContent_jqgTrade_StartPrice']"));
              for (int i = 0; i < eis_number.size(); i++) {
                  cell = eis_number.get(i);
                  eis = cell.getText();
                  if (!eis.trim().equals("")) {
                      cell_price = start_price.get(i);
                      price = cell_price.getText();
                      price_value = price.substring(0, price.length() - 4);
                      price_value = price_value.replaceAll(" ", "");
                      price_value = price_value.replaceAll(",", ".");
                      whole_amount = Double.valueOf(price_value) + whole_amount;
                      counter = counter + 1;
                  }
              }
              try {
                  TimeUnit.SECONDS.sleep(2);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              next.click();
              try {
                  TimeUnit.SECONDS.sleep(2);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              eis_number = null;
              start_price = null;
          }
          else { break;}
          try {
              TimeUnit.SECONDS.sleep(3);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          page_current = current_page.getAttribute("value");
          l = Integer.valueOf(page_current.trim());
          }
        WebElement abolished = driver.findElement(By.cssSelector("#lotStateTabs > ul > li:nth-child(10) > a"));
        abolished.click();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement page_count_abolished = driver.findElement(By.xpath("//*[@id=\"sp_1_BaseMainContent_MainContent_jqgTrade_toppager\"]"));
        WebElement current_page_abolished = driver.findElement(By.cssSelector("#BaseMainContent_MainContent_jqgTrade_toppager_center > table > tbody > tr > td:nth-child(4) > input"));
        WebElement next_abolished = driver.findElement(By.cssSelector("#next_t_BaseMainContent_MainContent_jqgTrade_toppager"));
        pages1 = null;
        pages1 = page_count_abolished.getText().toString();
        pages1 = pages1.replaceAll(" ","").trim();
        k = Integer.valueOf(pages1);
        page_current = current_page_abolished.getAttribute("value");
        l = Integer.valueOf(page_current.trim());
        int counter_abolished=0;
        while (true){
            if (l<k) {
                eis_number = driver.findElements(By.xpath("//td[@aria-describedby='BaseMainContent_MainContent_jqgTrade_OosNumber']"));
                start_price = driver.findElements(By.xpath("//td[@aria-describedby='BaseMainContent_MainContent_jqgTrade_StartPrice']"));
                for (int i = 0; i < eis_number.size(); i++) {
                    cell = eis_number.get(i);
                    eis = cell.getText();
                    if (!eis.trim().equals("")) {
                        cell_price = start_price.get(i);
                        price = cell_price.getText();
                        price_value = price.substring(0, price.length() - 4);
                        price_value = price_value.replaceAll(" ", "");
                        price_value = price_value.replaceAll(",", ".");
                        whole_amount_abolished = Double.valueOf(price_value) + whole_amount_abolished;
                        counter_abolished = counter_abolished + 1;
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                next_abolished.click();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                eis_number = null;
                start_price = null;
            }
            else { break;}
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            page_current = current_page_abolished.getAttribute("value");
            l = Integer.valueOf(page_current.trim());
        }
        System.out.println("Total number of lots: "+counter);
        System.out.print("Total start price: ");
        System.out.printf("%.2f",whole_amount);
        System.out.println();
        System.out.print("Total start price of canceled lots: ");
        System.out.printf("%.2f",whole_amount_abolished);
        total = whole_amount-whole_amount_abolished;
        System.out.println();
        System.out.print("Total: ");
        System.out.printf("%.2f",total);

    }
    @AfterClass
    public static void write () throws Exception{
        FileWriter report = new FileWriter("F://report.txt");
        report.write("Total number of lots: "+counter+"\n");
        report.write("Total start price: ");
        report.write(String.format("%(.2f",whole_amount));
        report.write("\nTotal start price of canceled lots: ");
        report.write(String.format("%(.2f",whole_amount_abolished));
        report.write("\nTotal: ");
        report.write(String.format("%(.2f",total));
        report.close();
        System.out.println("Recorded");
        driver.quit();

    }
}
