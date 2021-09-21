package stepDefinitions;

import java.io.IOException;

import org.json.JSONObject;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import utilities.Getconfigdata;

public class Hooks {

	public static String base_url;
	public static String access_key;
	
	//Environment is taken from /src/test/resources/EnvConfig.json 
	//The required environment can be updated in this json file 
	@Before
	public static void envConfig() throws IOException {
		String path="/src/test/resources/EnvConfig.json";
		JSONObject env_config =  new JSONObject(Getconfigdata.readJSONFile(path));
		base_url=(String) env_config.getJSONObject(env_config.getString("env")).get("BaseUrl");
		access_key=(String) env_config.getJSONObject(env_config.getString("env")).get("accessKey");
		RestAssured.baseURI=base_url;		
	}
}
