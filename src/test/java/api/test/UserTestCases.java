package api.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payloads.User;
import api.utilities.ExtentReportManager;
import io.restassured.response.Response;

import static org.testng.Assert.*;

public class UserTestCases {

	Faker faker = new Faker();
	User userPayload = new User();

	@BeforeClass
	public void setupData() {
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
	}

	@Test(priority = 1)
	void testPostUser() {
		Response response = UserEndPoints.createUser(userPayload);
		response.then().statusCode(200).log().all();

		// Assertions
		assertEquals(response.getStatusCode(), 200);
		// assertNotNull(response.getBody().jsonPath().get("id"));
		// assertEquals(response.getBody().jsonPath().getString("username"),
		// userPayload.getUsername());
		ExtentReportManager.logResponseDetails(response.getStatusCode(), response.getHeaders().asList(),
				response.getBody().asString());
	}

	@Test(priority = 2)
	void testGetUser() {
		Response response = UserEndPoints.readUser(userPayload.getUsername());
		response.then().statusCode(200).log().body();

		// Assertions assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getBody().jsonPath().getString("username"), userPayload.getUsername());
		assertEquals(response.getBody().jsonPath().getString("firstName"), userPayload.getFirstName());
		ExtentReportManager.logResponseDetails(response.getStatusCode(), response.getHeaders().asList(),
				response.getBody().asString());

	}

	@Test(priority = 3)
	void testUpdateUser() {
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());

		  System.out.println("**********************************"+this.userPayload.getUsername()+"**********************************");
	//	System.out.println(userPayload.getFirstName());

		Response responseAfterUpdate = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
		responseAfterUpdate.then().statusCode(200).log().body();

		// Assertions // assertEquals(responseAfterUpdate.getStatusCode(), 200); //
		//assertEquals(responseAfterUpdate.getBody().jsonPath().getString("firstName"), userPayload.getFirstName());
		ExtentReportManager.logResponseDetails(responseAfterUpdate.getStatusCode(),
				responseAfterUpdate.getHeaders().asList(), responseAfterUpdate.getBody().asString());
	}
	
	 @Test(priority = 4)
	    void testUpdateUserPassword() {
	      
		    String newPassword = faker.internet().password(5, 10);
	        userPayload.setPassword(newPassword);
	        
	        String username=this.userPayload.getUsername();
	        
	        System.out.println("**********************************"+this.userPayload.getUsername()+"**********************************");
	        
	        
	       Response responseafterPassUpdate = UserEndPoints.updateUserPassword(username, newPassword);
	       responseafterPassUpdate.then().statusCode(200).log().body();

	        // Assertions
	        assertEquals(responseafterPassUpdate.getStatusCode(), 200);
	   ExtentReportManager.logResponseDetails(responseafterPassUpdate.getStatusCode(), responseafterPassUpdate.getHeaders().asList(), responseafterPassUpdate.getBody().asString());
		 
		 
		 
		 
	    }
	
	
	

	@Test(priority = 5)
	void testDeleteUser() {
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		response.then().statusCode(200).log().all();

		// Assertions assertEquals(response.getStatusCode(), 200);
		ExtentReportManager.logResponseDetails(response.getStatusCode(), response.getHeaders().asList(),
				response.getBody().asString());
	}

	@Test(priority = 6)
	void testGetNonExistingUser() {

		// if new username so it is not in db get 404 error
		userPayload.setUsername(faker.name().username());

		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().statusCode(404).log().body();

		// Assertions assertEquals(response.getStatusCode(), 404);
		ExtentReportManager.logResponseDetails(response.getStatusCode(), response.getHeaders().asList(),
				response.getBody().asString());
	}

	@Test(priority = 7)
	void testCreateUserWithMinimumFields() {
		// Create a user with only mandatory fields User
		userPayload = new User();
		userPayload.setUsername(faker.name().username());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));

		Response response = UserEndPoints.createUser(userPayload);
		response.then().statusCode(200).log().all();

		// Assertions assertEquals(response.getStatusCode(), 200);
		//assertNotNull(response.getBody().jsonPath().get("id"));
	//	assertEquals(response.getBody().jsonPath().getString("username"), userPayload.getUsername());
		ExtentReportManager.logResponseDetails(response.getStatusCode(), response.getHeaders().asList(),
				response.getBody().asString());
	}

	@Test(priority = 8)
	void testCreateUserWithMaximumFields() {
		// Create a user with all fields filled with maximum allowed data
		User userPayload = new User();
		userPayload.setUsername(faker.lorem().characters(50));
		userPayload.setFirstName(faker.lorem().characters(50));
		userPayload.setLastName(faker.lorem().characters(50));
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.lorem().characters(100));
		userPayload.setPhone(faker.phoneNumber().cellPhone());

		Response response = UserEndPoints.createUser(userPayload);
		response.then().statusCode(200).log().all();

		// Assertions assertEquals(response.getStatusCode(), 200);
		//assertNotNull(response.getBody().jsonPath().get("id"));
		//assertEquals(response.getBody().jsonPath().getString("username"), userPayload.getUsername());
		ExtentReportManager.logResponseDetails(response.getStatusCode(), response.getHeaders().asList(),
				response.getBody().asString());
	}
	
	
	 @Test(priority = 9)
	    void testCreateUserWithInvalidEmail() {
	        // Test creating a user with invalid email format
	        User userPayload = new User();
	        userPayload.setUsername(faker.name().username());
	        userPayload.setEmail("invalid_email");
	        userPayload.setPassword(faker.internet().password(5, 10));

	        Response response = UserEndPoints.createUser(userPayload);
	        response.then().statusCode(400).log().all();

	        // Assertions
	       // assertEquals(response.getStatusCode(), 400);
	        ExtentReportManager.logResponseDetails(response.getStatusCode(), response.getHeaders().asList(), response.getBody().asString());
	    }
	
	
	
	
	
	
	
	

}
