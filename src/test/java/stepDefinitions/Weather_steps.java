package stepDefinitions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.testng.asserts.SoftAssert;

import Generic.APIEndpoints;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jdk.internal.org.jline.utils.Log;
import utilities.Json_handler;

public class Weather_steps {
	
	private ScenarioContext scenarioCtx;
	Scenario scenario;
	SoftAssert softAssert = new SoftAssert();
	RequestSpecification req;
	Response response;
	
	public Weather_steps(ScenarioContext scenarioCtx) {
		this.scenarioCtx = scenarioCtx;
	}
	
	@Before
	public void before(Scenario scenario) {
	    this.scenario = scenario;
	}
	
	@After
	public void after(Scenario scenario) {
	    this.scenario = scenario;
	    softAssert.assertAll();
	}
	
	@Given("user sends request using endpoint {string}")
	public void user_sends_request_using_endpoint(String endpoint) {
	   //gets generic requestspecification and acesses the endpoint
		try {
		   req=APIEndpoints.weatherAPI(endpoint);
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
	
	@Given("user sends required parameters as below")
	public void user_sends_required_parameters_as_below(DataTable requiredparams) {
		/*Accepts required parameters as Map and pass them to query parameters*/
		List<Map<String,String>> requiredparameters=requiredparams.asMaps(String.class,String.class);
		req.queryParam("access_key",Hooks.access_key);
		//requiredparameters.get(0).forEach((k,v)-> System.out.println("key= "+k+" value= "+v));
	    req.queryParams(requiredparameters.get(0));
	    //Log.info("Request: " + req.log().all());
	    scenario.log("Request: " + req.log().all());           //this extra logging is for display on cucumber reports
	}
	
	@Given("user sends optional parameters as below")
	public void user_sends_optional_parameters_as_below(DataTable optionalparams) {
		/*Accepts optional parameters, filters parameters having null value and prepares relevant query parameters*/
		List<Map<String,String>> optionalparameters=optionalparams.asMaps(String.class,String.class);
		optionalparameters.get(0).forEach((k,v)-> System.out.println("key= "+k+" value= "+v));
		Map<String,String> requestparams=new HashMap<String,String>();
		requestparams=optionalparameters.get(0).entrySet()
											   .stream()
											   .filter(entry -> entry.getValue() != null)												 
											   .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
		req.queryParams(requestparams);
		Log.info("Request: "+req.log().all());
		scenario.log("Request: "+req.log().all());
	}

	@Given("user hits APIendpoint {string} with parameters")
	public void user_hits_ap_iendpoint_with_parameters(String endpoint) {
		/*Hits passed endpoints with request prepared in above steps*/
	    response=req.when().get("/"+endpoint);
	    //Log.info("Response:"+ req.when().log().all());
	    scenario.log("Response:"+req.when().log().all());
	}
	
	@Given("user hits APIendpoint {string} with missing required parameters {string}")
	public void user_hits_ap_iendpoint_with_missing_required_parameters(String endpoint, String parameter) throws IOException {
	    req=APIEndpoints.weatherAPI(endpoint);
	    System.out.println("Boolean value: "+!(parameter.equalsIgnoreCase("access_key")));
	    if(!(parameter.equalsIgnoreCase("access_key")))
	    {
	    	req.queryParam("access_key",Hooks.access_key);
	    }
	    response=req.when().get("/"+endpoint);
	}
	
	@Given("user hits APIendpoint {string} with incorrect value for parameter {string}")
	public void user_hits_ap_iendpoint_with_incorrect_value_for_parameter(String endpoint, String parameter) throws IOException {
		 req=APIEndpoints.weatherAPI(endpoint).queryParam(parameter,Hooks.access_key+"123");
		 response=req.when().get("/"+endpoint);
	}
		
	@Then("Response status code is {int}")
	public void response_status_code_is(Integer status) {
		scenario.log("Response status code: "+response.getStatusCode());
//		softAssert.assertEquals(response.getStatusCode(), status);
		softAssert.assertTrue(status.equals(response.getStatusCode()));
	}

	@Then("Response {string} has value {string}")
	public void response_location_name_has_value(String responsekey,String value) {
		scenario.log("Responsekey"+Json_handler.getJsonPath(response, responsekey));
		softAssert.assertEquals(Json_handler.getJsonPath(response, responsekey), value);
	}
	
	@Then("Response {string} has value {int}")
	public void response_has_value(String responsekey, Integer value) {
		//scenario.log("Responsekey"+Json_handler.getJsonPath(response, responsekey));
		softAssert.assertEquals(Json_handler.getJsonPath(response, responsekey), value);
	}
}
