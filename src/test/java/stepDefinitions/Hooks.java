package stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        //write a code that will give you place id
        //execute this code only when place is is null

        PlaceValidationSteps m = new PlaceValidationSteps();
        if(PlaceValidationSteps.place_id==null) {
            m.add_place_payload_with("Sorin", "Romanian", "Europe");
            m.user_calls_with_http_request("addPlaceAPI", "POST");
            m.verify_place_id_created_maps_to_using("Sorin", "getPlaceAPI");
        }
    }
}
