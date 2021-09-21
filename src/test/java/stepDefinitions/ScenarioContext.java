package stepDefinitions;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import io.restassured.RestAssured;
import utilities.Getconfigdata;


//Scenario Context to pass the values between Steps files via Dependency Injection
public class ScenarioContext {
	
	//Stores key and values to pass through out the different steps
	private HashMap<String, Object>	scenarioCtx = new HashMap<String, Object>();
	
	public ScenarioContext(HashMap<String, Object> scenarioCtx) throws IOException {
		this.scenarioCtx = scenarioCtx;
		envConfig();
	}

	public Object getScenarioCtx(String key) {
		return scenarioCtx.get(key);
	}

	public void setScenarioCtx(String key, Object obj) {
		this.scenarioCtx.put(key, obj);
	}
	
	public void envConfig() throws IOException {
		String path="/src/test/resources/EnvConfig.json";
		JSONObject env_config =  new JSONObject(Getconfigdata.readJSONFile(path));
		setScenarioCtx("baseURL",env_config.getJSONObject(env_config.getString("env")).get("BaseUrl"));
		setScenarioCtx("access_key",env_config.getJSONObject(env_config.getString("env")).get("accessKey"));	
	}
}
