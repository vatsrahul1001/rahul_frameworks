package com.dropbox.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.dropbox.init.BaseClass;
import com.dropbox.pages.HomePage;

public class LoginTest extends BaseClass {
	@Parameters({ "expectedNameOfUser" })
	@Test(priority = 1, description = "Verify login of dropbox")
	@Description("Verify valid login of dropdox")
	@Epic("EP001")
	@Feature("Feature1: Login")
	@Story("Story:Dropbox login")
	@Step("Verify valid login for dropdown")
	public void validLogin(String expectedNameOfUser) {

		new HomePage().checkLoggedInUser(expectedNameOfUser);
	}

}
