package Classes;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import APIMethods.DeleteRequestMethod;
import APIMethods.PostRequestMethod;
import APIMethods.PutRequestMethod;

public class FactoryTestNG {
	@Factory
	public Object[] FactoryMethod() {
		Object[] data = null;
		try {
			File jsonFile = new File("./data.json").getAbsoluteFile();
			JsonNode jsonNode = new ObjectMapper().readTree(jsonFile);
			data = new Object[jsonNode.size()];
			int index = 0;
			for (JsonNode node : jsonNode) {
				JsonNode expectedResponseBody = node.get("expectedResponseBody");
				JsonNode requestedBody = node.get("requestedBody");
				JsonNode requestType = node.get("requestType");
				JsonNode route = node.get("route");
				JsonNode expectedStatusCode = node.get("expectedStatusCode");
				String type = node.get("requestType").toString();
				switch (type.replace("\"", "")) {
				case "POST":
					data[index] = new PostRequestMethod(route.toString(), requestedBody.toString(),
							expectedResponseBody.toString(), expectedStatusCode.toString());
					break;
				case "Put":
					data[index] = new PutRequestMethod(route.toString().replaceAll("\"", ""), requestedBody.toString(),
							expectedResponseBody.toString(), expectedStatusCode.toString());
					break;

				case "DELETE":
					data[index] = new DeleteRequestMethod(route.toString(), requestedBody.toString(),
							expectedStatusCode.toString());
					break;
				default:
					break;
				}
				index++;
			}
			return data;
		} catch (IOException e) {
			System.out.println(e);
		}
		return data;
	}

}
