package com.dropbox.helpers;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.dropbox.driver.DriverManager;
import com.dropbox.listeners.AllureListener;

public class GenericKeywords {
	private WebDriver driver;
	private AllureListener allure;

	public GenericKeywords() {
		driver = DriverManager.getDriver();  
		allure = new AllureListener();
	}

	@Step("navigating to application {0}")
	public void navigate(String url) {
		driver.get(url);

	}

	public boolean verifyElementPresent(WebElement element) {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(element));

			return true;
		} catch (Exception e) {
			System.out.println("Element not found " + e);
			return false;
		}
	}

	public boolean verifyElementPresent(By by) {
		try {
			new WebDriverWait(driver, 10).until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(by));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean verifyElementPresent(List<WebElement> elements) {

		try {

			new WebDriverWait(driver, 10).until(ExpectedConditions
					.visibilityOfAllElements(elements));
			return true;
		} catch (Exception e) {
			System.out.println("Element not found " + e);
			return false;
		}
	}

	public void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {

		}
	}

	@Step("click performed by user on: {1}")
	public void click(WebElement element, String locator) {
		element.click();

	}

	@Step("User Entered text: {1} in locator {2}")
	public void sendKeys(WebElement element, String text, String locator) {
		element.sendKeys(text);

	}

	public String getRandomString(int size) {
		return RandomStringUtils.randomAlphabetic(size).toUpperCase();
	}

	public void moveMouseToElement(WebElement element, String fieldName) {
		Actions action = new Actions(driver);
		action.moveToElement(element);
		action.perform();
		Allure.step("Move mouse over to the element (" + fieldName + ")");
	}

	@Step("Verify title of the page: {0}")
	public void verifypageTitle(String Expectedtitle) {

		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.titleContains(Expectedtitle));
		String actualTitle = driver.getTitle();
		System.out.println("Actual Page Title is " + actualTitle);
		Assert.assertEquals(actualTitle, Expectedtitle);
		allure.saveScreenShot(driver);
	}

	public boolean uploadFile(String filePath) {
		try {
			Process p = Runtime.getRuntime().exec(
					"./src/main/resources/autoitscripts/FileUpload.exe" + " \""
							+ filePath + "\"");
			while (p.isAlive()) {
				continue;
			}
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}
		return true;
	}

	public void enterTextSlow(String text, WebElement element) {
		for (char c : text.toCharArray()) {
			element.sendKeys(new StringBuilder().append(c).toString());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}