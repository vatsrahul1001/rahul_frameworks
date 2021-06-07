package com.dropbox.pages;

import io.qameta.allure.Step;

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

public class FolderFilePage {
	private WebDriver driver;
	private GenericKeywords gk;
	private LoginPage signInPage;
	private static String folderName;
	private static String fileName;
	private AllureListener allure;

	public FolderFilePage() {
		driver = DriverManager.getDriver();
		gk = new GenericKeywords();
		signInPage = new LoginPage();
		allure = new AllureListener();
		PageFactory.initElements(driver, this);
	}

	// FileFolderObjects
	@FindBy(xpath = "//span[contains(text() ,'New folder')]")
	private WebElement NewFolder_LBL;

	@FindBy(id = "new_folder_name_input")
	private WebElement NewFolderInput_TXT;

	@FindBy(xpath = "//span[(text()='Create') and @class='dig-Button-content']")
	private WebElement NewFolderCreate_BTN;

	@FindBy(xpath = "//h2[@data-testid='breadcrumb-segment']")
	private WebElement NewFolderHeading_TXT;

	// Upload Module
	@FindBy(xpath = "//span[@class='dig-Snackbar-message ']")
	private WebElement fileUploadedSuccessMessage_LBL;
	@FindBy(xpath = "//span[(text()='Close') and @class='dig-Button-content']")
	private WebElement fileUploadedSuccessfullyClose_BTN;

	@FindBy(xpath = "//span[contains(text() ,'Upload files')]")
	private WebElement FileUpload_LBL;

	@FindBy(xpath = "//span[(text()='Upload') and @class='dig-Button-content']")
	private WebElement FileUpload_BTN;

	@Step("Verifying New folder created")
	private void verifyNewfoldercreated() {
		gk.verifyElementPresent(NewFolderHeading_TXT);
		String actualNameOfFolder = NewFolderHeading_TXT.getText().trim();
		Assert.assertEquals(folderName, actualNameOfFolder,
				"Verify the new folder created with name " + folderName);
		gk.verifypageTitle(actualNameOfFolder + Constants.FOLDER_PAGE_TITLE);
		allure.saveScreenShot(driver);
	}

	@Step("Verifying New file uploaded")
	private void verifyNewFileUploaded() {
		gk.verifyElementPresent(fileUploadedSuccessMessage_LBL);
		String successMessage = fileUploadedSuccessMessage_LBL.getText().trim();
		System.out.println("actual " + successMessage);
		System.out.println("expected " + fileName);
		Assert.assertTrue(successMessage.contains(fileName),
				"Verify the new file upload with  name " + fileName);
		allure.saveScreenShot(driver);
	}

	@Step("Creating a New Folder")
	public void createFolder() {
		try {
			folderName = "MY Folder" + gk.getRandomString(8);
			gk.verifyElementPresent(NewFolder_LBL);
			gk.click(NewFolder_LBL, "New Folder option");
			gk.verifyElementPresent(NewFolderInput_TXT);
			gk.sendKeys(NewFolderInput_TXT, folderName, "New Folder Name");
			gk.click(NewFolderCreate_BTN, "NewFolder create Button");
			verifyNewfoldercreated();
		} catch (AssertionError e) {
			Assert.fail(e.getMessage());
		} catch (NoSuchElementException e) {
			Assert.fail(e.getMessage());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Step("Uploading a new file with name: {1}")
	public void uploadFile(String filePath, String fileName) {
		System.out.println("file path is " + filePath);

		FolderFilePage.fileName = fileName;
		String file_location = filePath + "\\" + FolderFilePage.fileName;
		System.out.println("file location is " + file_location);
		try {
			gk.verifyElementPresent(FileUpload_LBL);
			gk.click(FileUpload_LBL, "New File Upload option");
			gk.uploadFile(file_location);
			gk.verifyElementPresent(FileUpload_BTN);
			gk.click(FileUpload_BTN, "New File Upload Modal button");
			verifyNewFileUploaded();
			gk.verifyElementPresent(fileUploadedSuccessfullyClose_BTN);
			gk.click(fileUploadedSuccessfullyClose_BTN, "Close notification");
		} catch (AssertionError e) {
			Assert.fail(e.getMessage());
		} catch (NoSuchElementException e) {
			Assert.fail(e.getMessage());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}