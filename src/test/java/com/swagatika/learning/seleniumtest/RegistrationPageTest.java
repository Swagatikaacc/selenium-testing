package com.swagatika.learning.seleniumtest;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.swagatika.learning.seleniumtest.pageobjects.RegistrationPage;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class RegistrationPageTest extends BaseClass{
	RegistrationPage registrationPageObj;

	@Test
	public void validateCartPageTest() {
		registrationPageObj= new RegistrationPage(driver);			
						
		try {
			Response response = doGetRequest("https://randomuser.me/api/?seed=foobar");
			JsonPath jsonpath = response.jsonPath();
			// Verify info.seed is foobar
			Assert.assertEquals(jsonpath.getString("info.seed"), "foobar", "Correct");
			driver.get("https://demoqa.com/automation-practice-form");
			//Form submission
			registrationPageObj.enterFirstName(jsonpath.getString("results[0].name.first"));
			registrationPageObj.enterLastName(jsonpath.getString("results[0].name.last"));
			registrationPageObj.enterEmail(jsonpath.getString("results[0].email"));
			registrationPageObj.selectGender(jsonpath.getString("results[0].gender"));
			registrationPageObj.enterMobileNumber(jsonpath.getString("results[0].phone"));
			registrationPageObj.enterDateOfBirth(jsonpath.getString("results[0].dob.date"));
			registrationPageObj.enterSubject("Arts");
			registrationPageObj.selectHobby("Reading,Music");
			registrationPageObj.uploadFile(jsonpath.getString("results[0].picture.large"));
			String address = jsonpath.getString("results[0].location.street.number") + " "
					+ jsonpath.getString("results[0].location.street.name");
			registrationPageObj.enterAddress(address);
			registrationPageObj.selectState("Haryana");
			registrationPageObj.selectCity("Karnal");
			registrationPageObj.clickSubmitBtn();
			//Verify Name and Email
			Assert.assertEquals(jsonpath.getString("results[0].name.first") + " " + jsonpath.getString("results[0].name.last"),registrationPageObj.getStudentName() , "Successful Name Verification");	
			Assert.assertEquals(jsonpath.getString("results[0].email"),registrationPageObj.getStudentEmail() , "Successful Email Verification");
			registrationPageObj.clickCloseBtn();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}	
	  public static Response doGetRequest(String endpoint) {
	       RestAssured.defaultParser = Parser.JSON;

	       return
	           given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
	               when().get(endpoint).
	               then().contentType(ContentType.JSON).extract().response();
	   }
}
