package com.dropbox.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.dropbox.init.BaseClass;
import com.dropbox.pages.LoginPage;

public class InvalidLoginTest extends BaseClass {

	@Test(priority = 1, description = "Verify invalidlogin of dropbox")
	@Description("Verify invalidlogin of dropbox")
	@Epic("EP002")
	@Feature("Feature1: Login")
	@Story("Story:Dropbox login")
	@Step("Verify invalidlogin of dropbox")
	@Parameters({ "username", "password", "isvalidLogin" })
	public void InvalidLogin(String invalidusername, String invalidpassword,
			boolean isvalidLogin) {
		new LoginPage()
				.appLogin(invalidusername, invalidpassword, isvalidLogin);
	}
}