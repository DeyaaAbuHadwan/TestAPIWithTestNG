package APIMethods;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.internal.path.json.JsonPrettifier;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.internal.path.json.JsonPrettifier;

public class DeleteRequestMethod {
	int responseCode;
	String responseBody;
	String route, requestBody, expectedResponseBody, expectedStatusCode;
	boolean testStatus = true;

	public DeleteRequestMethod(String route, String requestBody, String expectedStatusCode) {
		this.route = route;
		this.requestBody = requestBody;
		this.expectedResponseBody = expectedResponseBody;
		this.expectedStatusCode = expectedStatusCode;
	}

	@Test
	public void deleteMethod() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com" + route.replaceAll("\"", "");
		Response response = RestAssured.delete(RestAssured.baseURI);
		ResponseBody body = response.getBody();
		this.responseCode = response.getStatusCode();
		this.responseBody = body.asPrettyString();
		this.testStatus = (this.responseCode == Integer.parseInt(expectedStatusCode));
	}

	public Boolean getTestStatus() {
		return testStatus;
	}
}
