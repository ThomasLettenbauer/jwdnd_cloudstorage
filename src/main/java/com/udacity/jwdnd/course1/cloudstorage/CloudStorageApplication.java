package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudStorageApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(CloudStorageApplication.class, args);
	}

	static void simLogon() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/signup");
		WebElement firstName = driver.findElement(By.name("firstName"));
		WebElement lastName = driver.findElement(By.name("lastName"));
		WebElement userName = driver.findElement(By.name("userName"));
		WebElement password = driver.findElement(By.name("password"));

		WebElement submit = driver.findElement(By.id("submit-button"));

		firstName.sendKeys("tom");
		lastName.sendKeys("tom");
		userName.sendKeys("tom");
		password.sendKeys("tom");
		submit.click();

		Thread.sleep(1000);

		WebElement loginUserName = driver.findElement(By.name("username"));
		WebElement loginPassword = driver.findElement(By.name("password"));
		WebElement loginSubmit = driver.findElement(By.id("submit-button"));

		loginUserName.sendKeys("tom");
		loginPassword.sendKeys("tom");
		loginSubmit.click();
	}

}
