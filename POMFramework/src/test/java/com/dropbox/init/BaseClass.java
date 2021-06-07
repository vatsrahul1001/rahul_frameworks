package com.dropbox.init;

import java.lang.reflect.Method;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.dropbox.driver.DriverManager;
import com.dropbox.pages.Firstpage;
import com.dropbox.pages.HomePage;
import com.dropbox.pages.LoginPage;

import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static boolean isvalidLogin;
	// public DriverManager driver;
	DriverManager dm = new DriverManager();
	public WebDriver driver;
	
	@Parameters({ "browser", "url", "language", "username", "password",
			"isvalidLogin" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method, String browser, String url,
			String language, String username, String password,
			boolean isvalidLogin) {
		driver = dm.initialize_driver(browser);
		new Firstpage().urlToNavigate(url);
		new LoginPage().updateLanguage(language);
		this.isvalidLogin = isvalidLogin;
		if (method.getDeclaringClass().getSimpleName()
				.equals("InvalidLoginTest")) {
			return;
		}
		new LoginPage().appLogin(username, password, isvalidLogin);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		try {
			if (isvalidLogin) {
				new HomePage().logOutFromApplication();
			}

		} catch (Exception e) {
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}

}