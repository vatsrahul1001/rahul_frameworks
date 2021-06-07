package com.dropbox.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

import org.testng.annotations.Test;

import com.dropbox.init.BaseClass;
import com.dropbox.pages.FolderFilePage;

public class UploadFileTests extends BaseClass {

	@Test(priority = 1, description = "Verify functionlity to do a new file upload")
	@Description("Verify functionlity to create a new folder for dropbox")
	@Epic("EP003")
	@Feature("Feature2: upload file")
	@Story("Story:Upload a new file")
	@Step("Verify functionlity to do a new file upload")
	public void uploadFile() {
		String filename = "Rahul_Resume.docx";
		String filePath = System.getProperty("user.dir")
				+ "\\src\\main\\resources\\testDataFiles";

		FolderFilePage fp = new FolderFilePage();
		fp.uploadFile(filePath, filename);
	}
}