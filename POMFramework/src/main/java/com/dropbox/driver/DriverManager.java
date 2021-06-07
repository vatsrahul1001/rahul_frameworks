package com.dropbox.driver;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;

public class DriverManager {
	public WebDriver driver;
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
	public WebDriver initialize_driver(String browser) {

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--disable-extensions");
			chromeOptions.addArguments("start-maximized");
			chromeOptions.addArguments("disable-infobars");
			chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver(chromeOptions);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			tdriver.set(driver);

		}

		else if (browser.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
			internetExplorerOptions.setCapability(
					CapabilityType.ACCEPT_SSL_CERTS, true);
			internetExplorerOptions
					.setPageLoadStrategy(PageLoadStrategy.NORMAL);
			internetExplorerOptions.takeFullPageScreenshot();
			internetExplorerOptions.destructivelyEnsureCleanSession();
			internetExplorerOptions.ignoreZoomSettings();
			driver = new InternetExplorerDriver(internetExplorerOptions);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			tdriver.set(driver);

		}

		else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions firefoxoptions = new FirefoxOptions();
			firefoxoptions.addArguments("--disable-extensions");
			firefoxoptions.addArguments("start-maximized");
			firefoxoptions.addArguments("disable-infobars");
			firefoxoptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new FirefoxDriver(firefoxoptions);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			tdriver.set(driver);

		}
		return getDriver();
	}

	public static synchronized WebDriver getDriver() {
		return tdriver.get();
	}


}
