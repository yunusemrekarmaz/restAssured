package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import mainPack.RestUtil;
import util.BookingMethods;

import java.io.IOException;


public class stepDefinitions {
    private Response resp;

    @Given("User Authenticates the system")
    public void userAuthenticatesTheSystem() throws IOException {
        resp = RestUtil.callMethod(BookingMethods.CreateToken);
        resp.then().assertThat().statusCode(200);
        resp.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ResponseSchemas/createTokenSchema.json"));
    }

    @Given("User tests POST method")
    public void userTestsPOSTMethod() throws IOException {
        resp = RestUtil.callMethod(BookingMethods.CreateBooking);
        resp.then().assertThat().statusCode(200);
        resp.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ResponseSchemas/postResponseSchema.json"));
        RestUtil.controlResponseBodyValues();
    }


    @Given("User tests GET method")
    public void userTestsGETMethod() throws IOException {
        resp = RestUtil.callMethod(BookingMethods.GetBooking);
        resp.then().assertThat().statusCode(200);
        resp.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ResponseSchemas/getResponseSchema.json"));
        RestUtil.controlResponseBodyValues();


    }

    @When("User verifies GetBookingIds method works properly")
    public void userVerifiesGetBookingIdsMethodWorksProperly() throws IOException {
        resp = RestUtil.callMethod(BookingMethods.GetBookingIds);
        resp.then().assertThat().statusCode(200);

    }

    @When("User verifies Update Booking PUT method works correctly")
    public void userVerifiesUpdateBookingPUTMethodWorksCorrectly() throws IOException {
        resp = RestUtil.callMethod(BookingMethods.UpdateBooking);
        resp.then().assertThat().statusCode(200);
        resp.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ResponseSchemas/putResponseSchema.json"));
        RestUtil.controlResponseBodyValues();


    }

    @When("User verifies Partial Update Booking method works correctly")
    public void userVerifiesPartialUpdateBookingMethodWorksCorrectly() throws IOException {
        resp = RestUtil.callMethod(BookingMethods.PartialUpdateBooking);
        resp.then().assertThat().statusCode(200);
        resp.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("ResponseSchemas/patchResponseSchema.json"));
        RestUtil.controlResponseBodyValues();

    }


    @Then("User verifies Delete Booking method works correctly")
    public void userVerifiesDeleteBookingMethodWorksCorrectly() throws IOException {
        resp = RestUtil.callMethod(BookingMethods.DeleteBooking);
        resp.then().assertThat().statusCode(201);
        resp = RestUtil.callMethod(BookingMethods.GetBooking);
        resp.then().assertThat().statusCode(404);


    }
}
