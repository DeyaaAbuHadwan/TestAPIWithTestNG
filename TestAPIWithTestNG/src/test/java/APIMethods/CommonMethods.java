package APIMethods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonMethods {
	
	public static boolean compareBodies(String actual, String expected) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualJson = mapper.readTree(actual);
		JsonNode expectedJson = mapper.readTree(expected);
		return actualJson.equals(expectedJson);
	}
}
