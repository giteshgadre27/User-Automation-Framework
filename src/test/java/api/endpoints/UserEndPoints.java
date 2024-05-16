package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payloads.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints {

	public static Response createUser(User payload) {

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)

				.when().post(Routes.post_url);
		return response;
	}

	public static Response readUser(String UserName) {

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.pathParam("username", UserName)

				.when().get(Routes.get_url);
		return response;
	}

	public static Response updateUser(String UserName, User payload) {

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.pathParam("username", UserName).body(payload)

				.when().put(Routes.update_url);
		return response;
	}
	
	/*
	 * public static Response updateUserPassword(String UserName, String password) {
	 * 
	 * Response response =
	 * given().contentType(ContentType.JSON).accept(ContentType.JSON)
	 * .pathParam("username", UserName).body(password)
	 * 
	 * .when().put(Routes.updatePass_url); return response; }
	 */
	
	public static Response updateUserPassword(String userName, String newPassword) {
	    Response response = given()
	            .contentType(ContentType.JSON)
	            .accept(ContentType.JSON)
	            .pathParam("username", userName)
	            .body("{\"password\": \"" + newPassword + "\"}") // Assuming password is sent in the request body
	            .when()
	            .put(Routes.updatePass_url);
	    return response;
	}
	

	public static Response deleteUser(String UserName) {

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.pathParam("username", UserName)

				.when().delete(Routes.delete_url);
		return response;
	}

	
}
