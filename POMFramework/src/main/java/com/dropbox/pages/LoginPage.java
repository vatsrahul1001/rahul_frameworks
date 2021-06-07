package com.dropbox.pages;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.dropbox.constant.Constants;
import com.dropbox.driver.DriverManager;
import com.dropbox.helpers.GenericKeywords;
import com.dropbox.listeners.AllureListener;

public class LoginPage {
	private WebDriver driver;
	private GenericKeywords gk;
	private AllureListener allure;
	private HomePage homePage;

	public LoginPage() {
		driver = DriverManager.getDriver();
		gk = new GenericKeywords();
		allure = new AllureListener();
		homePage = new HomePage();
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[@title='Choose a language']")
	public WebElement language_BTN;

	// login page objects
	@FindBy(linkText = "Sign in")
	private WebElement signIn_LNK;

	@FindBy(name = "login_email")
	public WebElement username_TXT;

	@FindBy(name = "login_password")
	private WebElement password_TXT;

	@FindBy(xpath = "//button[@type='submit' and ./div[contains(@class, 'signin')]]")
	private WebElement signIn_BTN;

	@FindBy(xpath = "//span[@class='error-message']")
	private WebElement errorMessage_LBL;

	@Step("Updating language as {0}")
	public void updateLanguage(String expectedLanguage) {
		boolean status = gk.verifyElementPresent(language_BTN);
		String selectedLanguage = language_BTN.getText();
		if (!selectedLanguage.equals(expectedLanguage)) {
			gk.click(language_BTN, "update language button");
			setLanguage(expectedLanguage);
		}
		gk.verifyElementPresent(language_BTN);
		gk.wait(10);
		selectedLanguage = language_BTN.getText();
		System.out.println("Expected Language is " + expectedLanguage);
		System.out.println("Actual Langusge is  " + selectedLanguage);
		Assert.assertEquals(expectedLanguage, selectedLanguage,
				"Verify language is changed to  (" + expectedLanguage + ")");
		allure.saveScreenShot(driver);
	}

	private void setLanguage(String language) {
		gk.verifyElementPresent(driver.findElement(By.partialLinkText(language)));
		gk.click(driver.findElement(By.partialLinkText(language)), language);
	}

	@Step("verify Invalid Login")
	private void verifyInvalidPasswordMessage() {
		try {
			gk.verifyElementPresent(errorMessage_LBL);
			String expectedEmptyUsernameErrorMessage = "Invalid email or password";
			String actualEmptyUsernameErrorMessage = errorMessage_LBL.getText()
					.trim();
			Assert.assertEquals(actualEmptyUsernameErrorMessage,
					expectedEmptyUsernameErrorMessage,
					"Verify the error message displayed is ("
							+ expectedEmptyUsernameErrorMessage + ")");
			allure.saveScreenShot(driver);
		} catch (AssertionError e) {
			allure.saveScreenShot(driver);
			Assert.fail(e.getMessage());
		} catch (NoSuchElementException e) {
			allure.saveScreenShot(driver);
			Assert.fail(e.getMessage());
		} catch (Exception e) {
			allure.saveScreenShot(driver);
			Assert.fail(e.getMessage());
		}
	}

	@Step("User login to Dropdown website")
	public void appLogin(String username, String password, boolean isvalidLogin) {
		gk.click(signIn_LNK, "sign in link");
		gk.verifypageTitle(Constants.LOGIN_PAGE_TITLE);
		gk.verifyElementPresent(username_TXT);
		gk.sendKeys(username_TXT, username, "username");
		gk.sendKeys(password_TXT, password, "password");
		gk.click(signIn_BTN, "sign in button");
		if (!isvalidLogin) {
			verifyInvalidPasswordMessage();
			return;
		}
		if (isvalidLogin) {
			verifyloginSuccess();
			gk.verifypageTitle(Constants.HOME_PAGE_TITLE);
			allure.saveScreenShot(driver);
		}
	}

	@Step("Verify Login Success")
	public void verifyloginSuccess() {
		try {
			boolean loginsuccess = gk
					.verifyElementPresent(homePage.avatar_loggedInUser_IMG);
			System.out.println("login status ==> " + loginsuccess);

			Assert.assertTrue(loginsuccess, "login successful");
			allure.saveScreenShot(driver);
		} catch (AssertionError e) {
			allure.saveScreenShot(driver);
			Assert.fail(e.getMessage());
		} catch (NoSuchElementException e) {
			allure.saveScreenShot(driver);
			Assert.fail(e.getMessage());
		} catch (Exception e) {
			allure.saveScreenShot(driver);
			Assert.fail(e.getMessage());
		}

	}
}