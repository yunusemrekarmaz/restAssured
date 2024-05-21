package mainPack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;
import util.BookingMethods;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.hamcrest.CoreMatchers.equalTo;
import java.util.Map;

@Getter
public class RestUtil {
    private static byte[] bytesFromJSON;
    private static String requestBody;
    private static Response resp;
    private static Map<String, String> headers;
    private static String token;
    private static String bookingId;
    private static JsonPath jsonPathEvaluator;
    private static BookingMethods method;
    public static Map<String, String> headersMap() throws JsonProcessingException {
        String defaultHeader = "{\n" +
                "  \"Accept\" : \"application/json\",\n" +
                "  \"Content-Type\" : \"application/json\"\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        headers = mapper.readValue(defaultHeader, Map.class);
        return headers;
    }
    public static void controlResponseBodyValues() throws JsonProcessingException {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
      Map<String,Object> reqBody= mapper.readValue(requestBody, Map.class);

      if(method.equals(BookingMethods.CreateBooking)){
        resp.then().assertThat().body("booking.firstname",equalTo(reqBody.get("firstname")));
        resp.then().assertThat().body("booking.lastname",equalTo(reqBody.get("lastname")));
        resp.then().assertThat().body("booking.depositpaid",equalTo(reqBody.get("depositpaid")));
        resp.then().assertThat().body("booking.bookingdates",equalTo(reqBody.get("bookingdates")));
        resp.then().assertThat().body("booking.additionalneeds",equalTo(reqBody.get("additionalneeds")));
      }
        else if(method.equals(BookingMethods.GetBooking)|| method.equals(BookingMethods.UpdateBooking)){
          resp.then().assertThat().body("firstname",equalTo(reqBody.get("firstname")));
          resp.then().assertThat().body("lastname",equalTo(reqBody.get("lastname")));
          resp.then().assertThat().body("depositpaid",equalTo(reqBody.get("depositpaid")));
          resp.then().assertThat().body("bookingdates",equalTo(reqBody.get("bookingdates")));
          resp.then().assertThat().body("additionalneeds",equalTo(reqBody.get("additionalneeds")));

        }
      else if(method.equals(BookingMethods.PartialUpdateBooking)){
          resp.then().assertThat().body("firstname",equalTo(reqBody.get("firstname")));
          resp.then().assertThat().body("lastname",equalTo(reqBody.get("lastname")));
      }

    }

    public static Response callMethod(BookingMethods methodName) throws IOException {
        method=methodName;
        headersMap();

        switch (methodName) {
            case CreateBooking:

                RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
                bytesFromJSON = Files.readAllBytes(Paths.get("src/main/resources/body/postData.json"));
                requestBody = new String(bytesFromJSON);
                headers.put("Authorization", token);
                resp = RestAssured.given()
                        .headers(headers)
                        .when()
                        .body(requestBody)
                        .request("POST")
                        .then()
                        .extract()
                        .response();
                jsonPathEvaluator = resp.jsonPath();
                bookingId = jsonPathEvaluator.get("bookingid").toString();
                break;

            case GetBooking:
                RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking/" + bookingId;
                resp = RestAssured.given()
                        .headers(headers)
                        .when()
                        .request("GET")
                        .then()
                        .extract()
                        .response();

                break;

            case GetBookingIds:
                RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
                headersMap().replace("Accept", "*/*");
                resp = RestAssured.given()
                        .headers(headers)
                        .when()
                        .request("GET")
                        .then()
                        .extract()
                        .response();

                break;

            case UpdateBooking:
                RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking/" + bookingId;
                bytesFromJSON = Files.readAllBytes(Paths.get("src/main/resources/body/updatePutData.json"));
                requestBody = new String(bytesFromJSON);
                //headers.put("cookie", "token=abc123");
                resp = RestAssured.given()
                        .headers(headers)
                        .auth()
                        .preemptive()
                        .basic("admin", "password123")
                        .when()
                        .body(requestBody)
                        .request("PUT")
                        .then()
                        .extract()
                        .response();

                break;

            case PartialUpdateBooking:

                RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking/" + bookingId;
                bytesFromJSON = Files.readAllBytes(Paths.get("src/main/resources/body/partialUpdatePatchData.json"));
                requestBody = new String(bytesFromJSON);
                resp= RestAssured.given()
                        .headers(headers)
                        .auth()
                        .preemptive()
                        .basic("admin", "password123")
                        .when()
                        .body(requestBody)
                        .request("PATCH")
                        .then()
                        .extract()
                        .response();

                break;

            case DeleteBooking:
                RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking/" + bookingId;
                headers.put("cookie", "token=abc123");
                resp = RestAssured.given()
                        .headers(headers)
                        .auth()
                        .preemptive()
                        .basic("admin", "password123")
                        .when()
                        .request("DELETE")
                        .then()
                        .extract()
                        .response();

                break;

            case CreateToken:
                RestAssured.baseURI = "https://restful-booker.herokuapp.com/auth";
                bytesFromJSON = Files.readAllBytes(Paths.get("src/main/resources/body/createAuthData.json"));
                requestBody = new String(bytesFromJSON);
                headers.replace("Content-Type", "application/json; charset=utf-8");
                headers.replace("Accept", "*/*");
                resp = RestAssured.given()
                        .headers(headers)
                        .when()
                        .body(requestBody)
                        .request("POST")
                        .then()
                        .extract()
                        .response();
                jsonPathEvaluator = resp.jsonPath();
                token = jsonPathEvaluator.get("token");
                token = "Bearer " + token;
                break;
        }

        return resp;
    }


}
