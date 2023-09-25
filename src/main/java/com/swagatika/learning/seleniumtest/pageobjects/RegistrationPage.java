package com.swagatika.learning.seleniumtest.pageobjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
	WebDriver driver;

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Locators
	By firstNameLoc = By.id("firstName");
	By lastNameLoc = By.id("lastName");
	By emailLoc = By.id("userEmail");
	By genderLoc = By.xpath("//input[@name='gender']");
	By mobileLoc = By.id("userNumber");
	By dobLoc = By.xpath("//div[@id='dateOfBirth']//input");
	By subjectLoc = By.id("subjectsInput");
	By hobbiesLoc = By.xpath("//input[contains(@id,'hobbies-checkbox')]");
	By fileUploadLoc = By.id("uploadPicture");
	By stateLoc = By.xpath("//div[text()='Select State']/following::input[1]");
	By cityLoc = By.xpath("//div[text()='Select City']/following::input");
	By addressLoc = By.id("currentAddress");
	By submiBtnLoc = By.id("submit");
	By studentNameLoc = By.xpath("//table//td[text()=\"Student Name\"]/following-sibling::td[1]");
	By studentEmailLoc = By.xpath("//table//td[text()=\"Student Email\"]/following-sibling::td[1]");
	By closeBtnlLoc = By.id("closeLargeModal");

	public void enterFirstName(String value) {
		WebElement firstNameElm = driver.findElement(firstNameLoc);
		firstNameElm.clear();
		firstNameElm.sendKeys(value);
	}

	public void enterLastName(String value) {
		WebElement lastNameElm = driver.findElement(lastNameLoc);
		lastNameElm.clear();
		lastNameElm.sendKeys(value);
	}

	public void enterEmail(String value) {
		WebElement emailElm = driver.findElement(emailLoc);
		emailElm.clear();
		emailElm.sendKeys(value);
	}

	public void selectGender(String value) {
		List<WebElement> genderRadioButtons = driver.findElements(genderLoc);
		for (WebElement genderRadio : genderRadioButtons) {
			if (value.equalsIgnoreCase(genderRadio.getAttribute("value")))
				((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", genderRadio);
		}
	}

	public void enterMobileNumber(String value) {
		driver.findElement(mobileLoc).sendKeys(value.replace("-", "").replaceFirst("0", ""));
	}

	public void enterDateOfBirth(String value) {
		try {
			WebElement dob = driver.findElement(dobLoc);
			dob.sendKeys(Keys.SPACE);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(dob));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", dob);
			Instant is = Instant.parse(value);
			ZoneId z = ZoneId.of("UTC");
			ZonedDateTime zdt = is.atZone(z);
			setDate(zdt.getMonthValue(), zdt.getDayOfMonth(), zdt.getYear());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setDate(int month, int date, int year) {
		WebElement selYear = driver.findElement(By.xpath("//*[@class='react-datepicker__year-select']"));
		selYear.click();
		String y = Integer.toString(year);
		selYear.sendKeys(y);
		selYear.click();

		WebElement selMonth = driver.findElement(By.xpath("//*[@class='react-datepicker__month-select']"));
		selMonth.click();
		String m = Month.of(month).toString();
		selMonth.sendKeys(m);
		selMonth.click();

		String d = Integer.toString(date);
		String xp = "//*[@class='react-datepicker__week']//*[text()='" + d + "']";
		WebElement selDate = driver.findElement(By.xpath(xp));
		selDate.click();
	}

	public void enterSubject(String value) {
		WebElement subjectElm = driver.findElement(subjectLoc);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", subjectElm);
		driver.findElement(subjectLoc).sendKeys(value);
		driver.findElement(subjectLoc).sendKeys(Keys.TAB);
	}

	public void selectHobby(String value) {
		List<String> hobbiesList = new ArrayList<String>(Arrays.asList(value.split(",")));
		for (String hobby : hobbiesList) {
			WebElement elm = driver.findElement(By.xpath("//*[text()='" + hobby + "']/preceding-sibling::input"));
			if (elm != null) {
				((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", elm);
			}
		}
	}

	public void uploadFile(String path) {
		WebElement fileElm = driver.findElement(fileUploadLoc);
		try {
			URL urlInput = new URL(path);
			BufferedImage urlImage = ImageIO.read(urlInput);
			File outputfile = new File("image.jpg");
			ImageIO.write(urlImage, "jpg", outputfile);
			fileElm.sendKeys(outputfile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void selectState(String value) {
		WebElement stateElm = driver.findElement(stateLoc);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", stateElm);
		stateElm.sendKeys(value);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stateElm.sendKeys(Keys.TAB);
	}

	public void selectCity(String value) {
		WebElement cityElm = driver.findElement(cityLoc);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", cityElm);
		cityElm.sendKeys(value);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cityElm.sendKeys(Keys.TAB);
	}

	public void enterAddress(String value) {
		driver.findElement(addressLoc).sendKeys(value);
	}

	public void clickSubmitBtn() {
		WebElement submitElm = driver.findElement(submiBtnLoc);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", submitElm);
	}

	public String getStudentName() {
		return driver.findElement(studentNameLoc).getText();
	}

	public String getStudentEmail() {
		return driver.findElement(studentEmailLoc).getText();
	}

	public void clickCloseBtn() {
		WebElement closeElm = driver.findElement(closeBtnlLoc);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", closeElm);
	}

}
