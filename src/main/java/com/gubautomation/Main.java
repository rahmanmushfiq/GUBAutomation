package com.gubautomation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Scanner;

public class Main {

    private static WebDriver driver;
    //    private static HtmlUnitDriver driver;
    private static String browser;
    private static String projectPath = System.getProperty("user.dir");
    private static String chromeDriver = projectPath + "/drivers/chromedriver/chromedriver";


    public static void main(String[] args) {
        while (true) {
            setBrowser();
            if (setBrowserConfig()) {
                break;
            }
        }
        GUBHomePage gubHomePage = new GUBHomePage(driver);
        gubHomePage.navigateToGUB();
        gubHomePage.findAllLinks();
        gubHomePage.checkLinks();
        tearDown();
    }

    private static void setBrowser() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please select your browser: ");
        browser = input.nextLine();
    }

    private static boolean setBrowserConfig() {
        try {
            if (browser.equalsIgnoreCase("Chrome")) {
                System.setProperty("webdriver.chrome.driver", chromeDriver);
                driver = new ChromeDriver();
                return true;
            }
//            if (browser.equalsIgnoreCase("headless")) {
//                driver = new HtmlUnitDriver();
//                return true;
//            }
        } catch (Exception exp) {
            System.out.println("Exception is: " + exp.getMessage());
            System.out.println("Exception Cause is: " + exp.getCause());
        }
        System.out.println("Invalid browser name ! Try again...");
        return false;
    }

    private static void tearDown() {
        driver.close();
        driver.quit();
    }
}
