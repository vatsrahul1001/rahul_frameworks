package com.dropbox.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

import org.testng.annotations.Test;

import com.dropbox.init.BaseClass;
import com.dropbox.pages.FolderFilePage;

public class CreateNewFolderTests extends BaseClass {

	@Test(priority = 1, description = "Verify functionlity to create a new folder for dropbox")
	@Description("Verify functionlity to create a new folder for dropbox")
	@Epic("EP002")
	@Feature("Feature2: Create new folder")
	@Story("Story:Dropdown folder create")
	@Step("Verify Creation of New folder")
	public void createFolder() {
		FolderFilePage fp = new FolderFilePage();
		fp.createFolder();
	}
}