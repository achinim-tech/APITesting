package CortiTest;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class userTest extends BaseClass{

@Test
    public void test_01_CreateUser() {

        String requestBody = "{\n" +
                "    \"id\": 909090,\n" +
                "    \"username\": \"Janedoe\",\n" +
                "    \"firstName\": \"Jane\",\n" +
                "    \"lastName\": \"Doe\",\n" +
                "    \"email\": \"john.doe@gmail.com\",\n" +
                "    \"password\": \"password123\",\n" +
                "    \"phone\": \"1234567890\"\n" +
                "}";


        Response response = given()
                .baseUri(BASE_URL)
                .basePath("/user")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("message", equalTo("909090"))
                .extract()
                .response();

    System.out.println("Test results: Test Case 1");
   System.out.println("Test results: "+response.body().prettyPrint());


    }
}
