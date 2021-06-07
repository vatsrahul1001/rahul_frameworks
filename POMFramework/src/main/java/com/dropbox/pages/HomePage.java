package com.dropbox.pages;

import io.qameta.allure.Step;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.dropbox.driver.DriverManager;
import com.dropbox.helpers.GenericKeywords;
import com.dropbox.listeners.AllureListener;

public class HomePage {
	private WebDriver driver;
	private GenericKeywords gk;
	private AllureListener allure;

	public HomePage() {
		allure = new AllureListener();
		driver = DriverManager.getDriver();
		gk = new GenericKeywords();
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='account-menu-v2__user-name']/span")
	private WebElement nameOfUser_LBL;

	// Homepage objects
	@FindBy(xpath = "//div[contains(@aria-labelledby, 'db-modal-title')]//a[normalize-space()='Show me around']")
	private WebElement showMeAround_LNK;

	@FindBy(xpath = "//div[contains(@class, 'avatar')]")
	public WebElement avatar_loggedInUser_IMG;

	@FindBy(xpath = "//div[contains(text() ,'Sign out')]")
	private WebElement signout_text;

	@Step("Verify user is logged out successfully ")
	public void logOutFromApplication() {
		gk.verifyElementPresent(avatar_loggedInUser_IMG);
		boolean status = gk.verifyElementPresent(signout_text);
		if (!status) {
			gk.click(avatar_loggedInUser_IMG, "Signed in user avatar");
		}
		gk.click(signout_text, "sign out");
		boolean wasLogOutSuccessful = gk
				.verifyElementPresent(new LoginPage().username_TXT);
		Assert.assertTrue(wasLogOutSuccessful, "Verify logout was successful");
	}

	@Step("Verify user is logged in successfully: {0}")
	public void checkLoggedInUser(String expectedNameOfUser) {
		try {
			gk.verifyElementPresent(avatar_loggedInUser_IMG);
			gk.click(avatar_loggedInUser_IMG, "user avatar icon");
			String actualNameOfUser = nameOfUser_LBL.getText()
					.replaceAll("Add photo", "").trim();
			Assert.assertEquals(expectedNameOfUser, actualNameOfUser,
					"Verify name of the user logged in is ("
							+ expectedNameOfUser + ")");
			allure.saveScreenShot(driver);
		} catch (AssertionError e) {
			Assert.fail(e.getMessage());
		} catch (NoSuchElementException e) {
			Assert.fail(e.getMessage());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}