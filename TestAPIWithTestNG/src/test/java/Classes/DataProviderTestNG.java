package Classes;

import static org.testng.Assert.assertTrue;

import java.io.File;
import io.restassured.*;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Map;
import org.testng.annotations.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import APIMethods.DeleteRequestMethod;
import APIMethods.PostRequestMethod;
import APIMethods.PutRequestMethod;

import com.fasterxml.jackson.core.TreeCodec.*;
import com.fasterxml.jackson.core.ObjectCodec.*;
import com.fasterxml.jackson.databind.*;

public class DataProviderTestNG {

	@DataProvider
	public Object[][] ProviderMethod() {
		Object[][] res;
		try {
			System.out.println("> Data Provider :");
			File jsonFile = new File("./data.json").getAbsoluteFile();
			JsonNode jsonNode = new ObjectMapper().readTree(jsonFile);
			res = new Object[jsonNode.size()][5];
			int i = 0;
			for (JsonNode node : jsonNode) {
				res[i][0] = node.get("expectedResponseBody");
				res[i][1] = node.get("requestedBody");
				res[i][2] = node.get("requestType");
				res[i][3] = node.get("route");
				res[i][4] = node.get("expectedStatusCode");
				i++;
			}
			return res;
		} catch (IOException e) {
			System.out.println(e);
		}
		return null;
	}

	@Test(dataProvider = "ProviderMethod")
	public void APITesting(Object expectedResBody, Object requestBody, Object requestType, Object route,
			Object expectedStatusCode) throws JsonIOException, JsonSyntaxException, IOException {
		String httpMethod = requestType.toString().replaceAll("\"", "");
		switch (httpMethod) {
		case "POST":
			PostRequestMethod testPost = new PostRequestMethod(route.toString(), requestBody.toString(),
					expectedResBody.toString(), expectedStatusCode.toString());
			testPost.postMethod();
			assertTrue(testPost.getTestStatus());
			break;
		case "DELETE":
			DeleteRequestMethod testDelete = new DeleteRequestMethod(route.toString(), requestBody.toString(),
					expectedStatusCode.toString());
			testDelete.deleteMethod();
			assertTrue(testDelete.getTestStatus());
			break;

		case "Put":
			PutRequestMethod testPut = new PutRequestMethod(route.toString(), requestBody.toString(),
					expectedResBody.toString(), expectedStatusCode.toString());
			testPut.putMethod();
			assertTrue(testPut.getTestStatus());
			break;
		default:
			assertTrue(false);
			break;
		}
	}
}