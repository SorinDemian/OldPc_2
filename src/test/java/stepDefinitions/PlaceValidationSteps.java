package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class PlaceValidationSteps extends Utils {

    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    TestDataBuild testDataBuild = new TestDataBuild();
    static String place_id;
    JsonPath js;

    @Given("Add Place payload with {string}, {string}, {string}")
    public void add_place_payload_with(String name, String language, String address) throws IOException {
        res = given().spec(requestSpecification())
                .body(testDataBuild.addPlacePayload(name,language,address));
    }

    @When("User calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {
//constructor will be called with value of resource witch you pass
        APIResources resourcesAPI = APIResources.valueOf(resource);
        System.out.println(resourcesAPI.getResource());

        resspec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        if(method.equalsIgnoreCase("POST")) {
            response = res.when().post(resourcesAPI.getResource());
        }else if(method.equalsIgnoreCase("GET")){
            response = res.when().get(resourcesAPI.getResource());
        }
    }

    @Then("The API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer int1) {
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("{string} response body is {string}")
    public void response_body_is(String keyValue, String expectedValue) {
        Assert.assertEquals(getJsonPath(response,keyValue), expectedValue);
    }

    @Then("Verify place_id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
        //requestSpec
        place_id = getJsonPath(response,"place_id");
        res = given().spec(requestSpecification()).queryParam("place_id", place_id);
        user_calls_with_http_request(resource, "GET");
        String actualName = getJsonPath(response,"name");
        Assert.assertEquals(actualName, expectedName);

    }

    @Given("DeletePlace Payload")
    public void delete_place_payload() throws IOException {
        res = given().spec(requestSpecification()).body(testDataBuild.deletePlacePayload(place_id));
    }
}
