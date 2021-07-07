package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void accessLoginOrSignupPage() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Thread.sleep(2000);
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/signup");
		Thread.sleep(2000);
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(2)
	public void accessSecuredPagesWithoutLogin() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(2000);
		Assertions.assertNotEquals("Home", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/result");
		Thread.sleep(2000);
		Assertions.assertNotEquals("Result", driver.getTitle());
	}

	// Sign up a new user
	// log in
	// verify that the home page is accessible
	// log out
	// verify that the home page is no longer accessible

	@Test
	@Order(3)
	public void signUpAndLogout() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		signUp("Tom", "Tester", "tom", "password");

		login("tom", "password");

		// home page is accessible?
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(1000);
		Assertions.assertEquals("Home", driver.getTitle());

		logout();

		// home page is not accessible?
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(1000);
		Assertions.assertNotEquals("Home", driver.getTitle());

	}


	// create a note
	// verify it is displayed
	@Test
	@Order(4)
	public void createNote() throws InterruptedException {

		logout();
		signUp("Tom", "NoteTester", "tomnote", "password");
		login("tomnote", "password");

		// home page
		driver.get("http://localhost:" + this.port + "/home");

		Thread.sleep(1000);

		WebElement navNotesTab = driver.findElement(By.id("nav-notes-tab"));

		navNotesTab.click();

		Thread.sleep(1000);

		WebElement addNoteButton = driver.findElement(By.id("new-note-button"));

		addNoteButton.click();

		Thread.sleep(1000);

		WebElement noteTitle = driver.findElement(By.id("note-title"));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		WebElement saveNoteButton = driver.findElement(By.id("note-save-changes"));

		noteTitle.sendKeys("Title");
		noteDescription.sendKeys("Description");

		Thread.sleep(1000);

		saveNoteButton.click();

		Thread.sleep(1000);

		// go Home and to Notes tab
		driver.get("http://localhost:" + this.port + "/home");
		navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();

		Thread.sleep(1000);

		// Note exists
		WebElement firstNoteTitle = driver.findElement(By.id("note-title-0"));
		WebElement firstNoteDescription = driver.findElement(By.id("note-description-0"));

		Assertions.assertEquals("Title", firstNoteTitle.getText());

		Assertions.assertEquals("Description", firstNoteDescription.getText());


	}


	// edit an existing note
	// verify that the changes are displayed
	@Test
	@Order(5)
	public void editNote() throws InterruptedException {

		login("tomnote", "password");

		// home page
		driver.get("http://localhost:" + this.port + "/home");

		Thread.sleep(1000);

		WebElement navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();
		Thread.sleep(1000);

		WebElement firstNoteEditButton = driver.findElement(By.id("edit-note-0"));
		firstNoteEditButton.click();
		Thread.sleep(1000);

		WebElement noteTitle = driver.findElement(By.id("note-title"));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		WebElement saveNoteButton = driver.findElement(By.id("note-save-changes"));

		noteTitle.clear();
		noteTitle.sendKeys("Title New");
		noteDescription.clear();
		noteDescription.sendKeys("Description New");

		saveNoteButton.click();
		Thread.sleep(1000);

		// go Home and to Notes tab
		driver.get("http://localhost:" + this.port + "/home");
		navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();

		Thread.sleep(1000);

		// Note exists
		WebElement firstNoteTitle = driver.findElement(By.id("note-title-0"));
		WebElement firstNoteDescription = driver.findElement(By.id("note-description-0"));

		Assertions.assertEquals("Title New", firstNoteTitle.getText());

		Assertions.assertEquals("Description New", firstNoteDescription.getText());

	}


	// delete a note
	// verify that the note is no longer displayed
	@Test
	@Order(6)
	public void deleteNote() throws InterruptedException {

		login("tomnote", "password");

		// home page
		driver.get("http://localhost:" + this.port + "/home");

		Thread.sleep(1000);

		WebElement navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();
		Thread.sleep(1000);

		WebElement firstNoteDeleteButton = driver.findElement(By.id("delete-note-0"));
		firstNoteDeleteButton.click();
		Thread.sleep(1000);

		// go Home and to Notes tab
		driver.get("http://localhost:" + this.port + "/home");
		navNotesTab = driver.findElement(By.id("nav-notes-tab"));
		navNotesTab.click();

		Thread.sleep(1000);

		// Note does not exist
		boolean noteExists = true;

		try {
		WebElement firstNoteTitle = driver.findElement(By.id("note-title-0")); }
		catch (NoSuchElementException e) { noteExists = false; }

		Assertions.assertEquals(false, noteExists);

	}

	// create a set of credentials
	// verify that they are displayed
	// verify that the displayed password is encrypted
	@Test
	@Order(7)
	public void createAndViewCredentials() throws InterruptedException {

		logout();
		signUp("Tom", "CredentialTester", "tomcred", "password");
		login("tomcred", "password");

		// home page
		driver.get("http://localhost:" + this.port + "/home");

		Thread.sleep(1000);

		WebElement navCredentialsTab = driver.findElement(By.id("nav-credentials-tab"));

		navCredentialsTab.click();

		Thread.sleep(1000);

		WebElement addCredentialButton = driver.findElement(By.id("new-credential-button"));

		addCredentialButton.click();

		Thread.sleep(1000);

		WebElement credUrl = driver.findElement(By.id("credential-url"));
		WebElement credUserName = driver.findElement(By.id("credential-username"));
		WebElement credPassword = driver.findElement(By.id("credential-password"));
		WebElement saveCredButton = driver.findElement(By.id("cred-save-changes"));

		credUrl.sendKeys("http://www.superlearning.tv");
		credUserName.sendKeys("paul.panther");
		credPassword.sendKeys("mypassword");

		Thread.sleep(1000);

		saveCredButton.click();

		Thread.sleep(1000);

		// go Home and to Credentials tab
		driver.get("http://localhost:" + this.port + "/home");
		navCredentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		navCredentialsTab.click();

		Thread.sleep(1000);

		// Note exists
		WebElement firstCredentialUrl = driver.findElement(By.id("cred-url-0"));
		WebElement firstCredentialUserName = driver.findElement(By.id("cred-username-0"));
		WebElement firstCredentialPassword = driver.findElement(By.id("cred-password-0"));

		Assertions.assertEquals("http://www.superlearning.tv", firstCredentialUrl.getText());
		Assertions.assertEquals("paul.panther", firstCredentialUserName.getText());
		Assertions.assertNotEquals("mypassword", firstCredentialPassword.getText());

	}

	// view an existing set of credentials
	// verify that the viewable password is unencrypted
	// edit the credentials
	// verify that the changes are displayed
	@Test
	@Order(8)
	public void viewAndEditCredentals() throws InterruptedException {

		logout();
		login("tomcred", "password");

		// home page
		driver.get("http://localhost:" + this.port + "/home");

		Thread.sleep(1000);

		WebElement navCredentialsTab = driver.findElement(By.id("nav-credentials-tab"));

		navCredentialsTab.click();

		Thread.sleep(1000);

		WebElement firstCredentialEditButton = driver.findElement(By.id("edit-cred-0"));
		firstCredentialEditButton.click();
		Thread.sleep(1000);

		WebElement credUrl = driver.findElement(By.id("credential-url"));
		WebElement credUserName = driver.findElement(By.id("credential-username"));
		WebElement credPassword = driver.findElement(By.id("credential-password"));
		WebElement saveCredButton = driver.findElement(By.id("cred-save-changes"));

		// password shall be unencrypted
		Assertions.assertEquals("mypassword", credPassword.getAttribute("value"));

		credUrl.clear();
		credUrl.sendKeys("http://www.superduperlearning.tv");
		credUserName.clear();
		credUserName.sendKeys("john.panther");

		Thread.sleep(1000);

		saveCredButton.click();

		Thread.sleep(1000);

		// go Home and to Credentials tab
		driver.get("http://localhost:" + this.port + "/home");
		navCredentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		navCredentialsTab.click();

		Thread.sleep(1000);

		// Cred changes are displayed?
		WebElement firstCredentialUrl = driver.findElement(By.id("cred-url-0"));
		WebElement firstCredentialUserName = driver.findElement(By.id("cred-username-0"));
		WebElement firstCredentialPassword = driver.findElement(By.id("cred-password-0"));

		Assertions.assertEquals("http://www.superduperlearning.tv", firstCredentialUrl.getText());
		Assertions.assertEquals("john.panther", firstCredentialUserName.getText());
		Assertions.assertNotEquals("mypassword", firstCredentialPassword.getText());

	}

	// delete an existing set of credentials
	// verify that the credentials are no longer displayed
	@Test
	@Order(9)
	public void deleteCredentials() throws InterruptedException {

		login("tomcred", "password");

		// home page
		driver.get("http://localhost:" + this.port + "/home");

		Thread.sleep(1000);

		WebElement navCredentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		navCredentialsTab.click();
		Thread.sleep(1000);

		WebElement firstCredentialDeleteButton = driver.findElement(By.id("delete-cred-0"));
		firstCredentialDeleteButton.click();
		Thread.sleep(1000);

		// go Home and to Credentials tab
		driver.get("http://localhost:" + this.port + "/home");
		navCredentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		navCredentialsTab.click();

		Thread.sleep(1000);

		// Credential does not exist
		boolean credentialExists = true;

		try {
			WebElement firstCredentialUrl = driver.findElement(By.id("cred-url-0")); }
		catch (NoSuchElementException e) { credentialExists = false; }

		Assertions.assertEquals(false, credentialExists);

	}


	// helper functions
	public void signUp(String theFirstName, String theLastName, String theUserName, String thePassword) throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement firstName = driver.findElement(By.name("firstName"));
		WebElement lastName = driver.findElement(By.name("lastName"));
		WebElement userName = driver.findElement(By.name("userName"));
		WebElement password = driver.findElement(By.name("password"));

		WebElement submit = driver.findElement(By.id("submit-button"));

		firstName.sendKeys(theFirstName);
		lastName.sendKeys(theLastName);
		userName.sendKeys(theUserName);
		password.sendKeys(thePassword);
		submit.click();

		Thread.sleep(1000);
	}

	public void login(String theUserName, String thePassword) throws InterruptedException {

		driver.get("http://localhost:" + this.port + "/login");

		Thread.sleep(1000);

		WebElement loginUserName = driver.findElement(By.name("username"));
		WebElement loginPassword = driver.findElement(By.name("password"));
		WebElement loginSubmit = driver.findElement(By.id("submit-button"));

		loginUserName.sendKeys(theUserName);
		loginPassword.sendKeys(thePassword);
		loginSubmit.click();

		Thread.sleep(1000);
	}

	public void logout() throws InterruptedException {

		driver.get("http://localhost:" + this.port + "/home");

		System.out.println("***** Logout ***** " + driver.getTitle());

		if (driver.getTitle().equals("Home")) {

			WebElement logoutSubmit = driver.findElement(By.id("logout-button"));

			// logout user
			logoutSubmit.click();

			Thread.sleep(1000);
		}

	}

}
