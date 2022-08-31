package APIMethods;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.RestAssured;
import io.restassured.internal.path.json.JsonPrettifier;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.internal.path.json.JsonPrettifier;

public class PostRequestMethod {
	int responseCode;
	public String route, requestBody, expectedResponseBody, expectedStatusCode;
	boolean testStatus = false;

	public PostRequestMethod(String route, String requestBody, String expectedResponseBody, String expectedStatusCode) {
		this.route = route;
		this.requestBody = requestBody;
		this.expectedResponseBody = expectedResponseBody;
		this.expectedStatusCode = expectedStatusCode;
	}

	@Test
	public void postMethod() throws JsonMappingException, NumberFormatException, JsonProcessingException {
		System.out.println("Post Method");
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com" + route.replaceAll("\"", "");
		RequestSpecification request = RestAssured.given().body(requestBody);
		request.header("Content-Type", "application/json");
		Response response = request.post();
		ResponseBody body = response.getBody();
		this.responseCode = response.getStatusCode();
		this.testStatus = (this.responseCode == Integer.parseInt(expectedStatusCode)
				&& CommonMethods.compareBodies(expectedResponseBody, body.asString()));

	}

	public Boolean getTestStatus() {
		return testStatus;
	}
}
