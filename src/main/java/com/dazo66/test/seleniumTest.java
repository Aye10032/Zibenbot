package com.dazo66.test;

import com.aye10032.Utils.IOUtil;
import com.aye10032.Utils.ImgUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class seleniumTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", ".\\res\\ChromeDriver\\chromedriver.exe");


        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");
        options.addArguments("disable-infobars");
        options.addArguments("lang=zh_CN.UTF-8");
        options.addArguments("user-agent=User-Agent:Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5X Build/MMB29P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.96 Mobile Safari/537.36 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");

        //Initiating your chromedriver
        WebDriver driver = new ChromeDriver(options);
        //Applied wait time
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //maximize window
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(1, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        //open browser with desried URL
        long current = System.currentTimeMillis();
        driver.get("https://ngabbs.com/read.php?tid=21862305&rand=117");
        System.out.println((float) (System.currentTimeMillis() - current) / 1000);
        current = System.currentTimeMillis();
        //Thread.sleep(4000);
        JavascriptExecutor driver_js= ((JavascriptExecutor) driver);
        Double width = Double.valueOf(driver_js.executeScript(
                "return document.getElementsByTagName('html')[0].getBoundingClientRect().width;").toString());
        Double height = Double.valueOf(driver_js.executeScript("return document.getElementsByTagName('html')[0].getBoundingClientRect().height;").toString());
        System.out.println(height);
        driver.manage().window().setSize(new Dimension(1366, height.intValue()));
        byte[] bytes = driver.findElement(By.tagName("html")).getScreenshotAs(OutputType.BYTES);
        bytes = ImgUtils.compress(bytes, "png");
        IOUtil.saveFileWithBytes("test/" + System.currentTimeMillis() + ".png", bytes);
        //closing the browser
        driver.get("about:blank");
        System.out.println(driver);
    }





}
