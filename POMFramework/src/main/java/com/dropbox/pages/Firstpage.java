package com.dropbox.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.dropbox.constant.Constants;
import com.dropbox.driver.DriverManager;
import com.dropbox.helpers.GenericKeywords;

public class Firstpage {
	private WebDriver driver;
	private GenericKeywords gk;
	private LoginPage loginPage;

	public Firstpage() {
		driver = DriverManager.getDriver();
		gk = new GenericKeywords();
		loginPage = new LoginPage();
		PageFactory.initElements(driver, this);
	}

	public void urlToNavigate(String url) {
		gk.navigate(url);
		boolean didPageLoadSuccessfully = gk
				.verifyElementPresent(loginPage.language_BTN);
		Assert.assertTrue(didPageLoadSuccessfully,
				"Verify the application has been opened successfully");
		gk.verifypageTitle(Constants.FIRST_PAGE_TITLE);
	}
}