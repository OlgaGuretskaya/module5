package singleton;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import pages.GmailInboxPage;
import pages.GmailLoginPage;
import pages.GmailMainPage;
import pages.GmailSpamPage;
import utils.*;

public class GmailTest {

	private GmailLoginPage gmailLoginPage;
	private GmailMainPage gmailMainPage;
	private GmailInboxPage gmailInboxPage;
	private GmailSpamPage gmailSpamPage;
	private Utils utils;

	@Before
	public void before() {
		gmailLoginPage = new GmailLoginPage();
		gmailMainPage = new GmailMainPage();
		gmailInboxPage = new GmailInboxPage();
		gmailSpamPage = new GmailSpamPage();
		utils = new Utils();

		gmailLoginPage.init(WebDriverSingleton.getWebDriverInstance());
		gmailMainPage.init(WebDriverSingleton.getWebDriverInstance());
		gmailInboxPage.init(WebDriverSingleton.getWebDriverInstance());
		gmailSpamPage.init(WebDriverSingleton.getWebDriverInstance());
	}

	@After
	public void after() {
		WebDriverSingleton.getWebDriverInstance().quit();
	}

	@org.junit.Test
	public void checkSpamMessage() {
		String firstMessageSubject = utils.generateRandomSentence();
		String secondMessageSubject = utils.generateRandomSentence();
		String firstMessageText = utils.generateRandomSentence();
		String secondMessageText = utils.generateRandomSentence();

		WebDriverSingleton.getWebDriverInstance().navigate().to(Constants.URL);
		gmailLoginPage.logIn(Constants.FIRST_ACCOUNT_EMAIL, Constants.FIRST_ACCOUNT_PASSWORD);
		TimeOut.waitThreeSeconds();
		gmailMainPage.sendMessage(Constants.SECOND_ACCOUNT_EMAIL, firstMessageSubject, firstMessageText);
		TimeOut.waitThreeSeconds();
		gmailMainPage.signOut();
		TimeOut.waitThreeSeconds();
		gmailLoginPage.logIn(Constants.SECOND_ACCOUNT_EMAIL, Constants.SECOND_ACCOUNT_PASSWORD);
		TimeOut.waitThreeSeconds();
		gmailInboxPage.moveToSpam();
		TimeOut.waitThreeSeconds();
		gmailMainPage.signOut();
		TimeOut.waitThreeSeconds();
		gmailLoginPage.logIn(Constants.FIRST_ACCOUNT_EMAIL, Constants.FIRST_ACCOUNT_PASSWORD);
		TimeOut.waitThreeSeconds();
		gmailMainPage.sendMessage(Constants.SECOND_ACCOUNT_EMAIL, secondMessageSubject, secondMessageText);
		TimeOut.waitThreeSeconds();
		gmailMainPage.signOut();
		TimeOut.waitThreeSeconds();
		gmailLoginPage.logIn(Constants.SECOND_ACCOUNT_EMAIL, Constants.SECOND_ACCOUNT_PASSWORD);
		TimeOut.waitThreeSeconds();
		Assert.assertEquals(secondMessageSubject, gmailSpamPage.checkSpam().getText());
	}
}
