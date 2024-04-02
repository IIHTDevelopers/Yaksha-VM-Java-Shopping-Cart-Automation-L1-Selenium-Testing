package testcases;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import coreUtilities.testutils.ApiHelper;
import coreUtilities.utils.FileOperations;
import pages.ShoppingCartAutomationPages;
import pages.StartupPage;
import testBase.AppTestBase;
import testdata.LocatorsFactory;

public class ShoppingCartAutomationL1TestCases extends AppTestBase
{
	Map<String, String> configData;
	Map<String, String> loginCredentials;
	String expectedDataFilePath = testDataFilePath+"expected_data.json";
	StartupPage startupPage;
	ShoppingCartAutomationPages practiceAutomationInstance;
	LocatorsFactory LocatorsFactoryInstance = new LocatorsFactory(driver);

	@Parameters({"browser", "environment"})
	@BeforeClass(alwaysRun = true)
	public void initBrowser(String browser, String environment) throws Exception {
		configData = new FileOperations().readJson(config_filePath, environment);
		configData.put("url", configData.get("url").replaceAll("[\\\\]", ""));
		configData.put("browser", browser);
		
		boolean isValidUrl = new ApiHelper().isValidUrl(configData.get("url"));
		Assert.assertTrue(isValidUrl, configData.get("url")+" might be Server down at this moment. Please try after sometime.");
		initialize(configData);
		startupPage = new StartupPage(driver);
	}
		@Test(priority = 1, groups = {"sanity"}, description="Verify navigate to the shopping cart home page")
		public void verifyNavigateTotTheShoppingCartpage() throws Exception {
			practiceAutomationInstance= new ShoppingCartAutomationPages(driver);
			Map<String, String> expectedData = new FileOperations().readJson(expectedDataFilePath, "homepage");
			Assert.assertEquals(practiceAutomationInstance.verifyNavigateTotTheShoppingCartpage(), expectedData.get("homepageTitle"));
			Assert.assertTrue(LocatorsFactoryInstance.getShopIconOption(driver).isDisplayed(), "shop option menu is not present in the current page, Please check manually");
	}
		@Test(priority = 2, groups = {"sanity"}, description="Check Shop Menu Option is present or not")
		public void checkShopMenuIsPresent() throws Exception {
			practiceAutomationInstance= new ShoppingCartAutomationPages(driver);
			Assert.assertTrue(practiceAutomationInstance.verifyShopOptionIsPresent(), "shop option menu is not present, please check manually");
			Assert.assertTrue(LocatorsFactoryInstance.getShopIconOption(driver).isDisplayed(), "shop option menu is not present in the current page, Please check manually");
	}	
		@Test(priority = 3, groups = {"sanity"}, description="Click on “Shop” Menu Option and validate successfully navigate to the shop page or not")
		public void clickOnShopMenuAndValidateTheTitle() throws Exception {
			practiceAutomationInstance= new ShoppingCartAutomationPages(driver);
			Map<String, String> expectedData = new FileOperations().readJson(expectedDataFilePath, "Shop_page");
			Assert.assertEquals(practiceAutomationInstance.clickOnShopMenuandValidateTheTitle(), expectedData.get("Shop_page_title"));
			Assert.assertTrue(LocatorsFactoryInstance.androidProductCategoryIsPresent(driver).isDisplayed(), "Android category menu is not present in the current page, Please check manually");
	}	
	
		
						
		@AfterClass(alwaysRun = true)
		public void tearDown() {
		System.out.println("before closing the browser");
		browserTearDown();
		}
				
		@AfterMethod
		public void retryIfTestFails() throws Exception {
		startupPage.navigateToUrl(configData.get("url"));
		}
}