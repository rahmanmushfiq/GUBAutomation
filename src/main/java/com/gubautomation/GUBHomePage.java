package com.gubautomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GUBHomePage {
    private final WebDriver driver;

    public GUBHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToGUB() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://green.edu.bd/");
    }

    public List findAllLinks() {
        List<WebElement> elementList;
        List finalList = new ArrayList();
        elementList = driver.findElements(By.tagName("a"));
        elementList.addAll(driver.findElements(By.tagName("img")));
        for (WebElement element : elementList) {
            if (element.getAttribute("href") != null) {
                finalList.add(element);
            }
        }
        return finalList;
    }

    private String isLinkBroken(URL url) throws Exception {

        String response = null;
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        try {
            connection.connect();
            response = connection.getResponseMessage();
            connection.disconnect();
            return response;

        } catch (Exception exp) {
            return exp.getMessage();
        }
    }

    public void checkLinks() {
        List<WebElement> allImages = findAllLinks();
        System.out.println("Total Links Found: " + allImages.size());
        for (WebElement element : allImages) {
            try {
                System.out.println("URL: " + element.getAttribute("href") + " -> returned " + isLinkBroken(new URL(element.getAttribute("href"))));
            } catch (Exception exp) {
                System.out.println("At " + element.getText() + ": Exception Cause is: " + exp.getMessage());
            }
        }

    }
}
