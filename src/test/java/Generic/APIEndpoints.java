package Generic;

import java.io.IOException;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

/*This class defines all HTTP method in the application; and returns generic behavior to further use in code */
public class APIEndpoints {
			
		public static RequestSpecification weatherAPI(String endPoint) throws IOException {
	        RequestSpecification request = RestAssured.given();
	        request.header("Content-Type", "application/json");
	        return request;      
		}
		

}
