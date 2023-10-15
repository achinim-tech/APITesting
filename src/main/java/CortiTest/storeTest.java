package CortiTest;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class storeTest extends BaseClass{


     int orderId;

    @Test (priority = 1)
    public void test_01_GetInventory()
    {
        Response response= given()
                .baseUri(BASE_URL)
                .basePath("/store/inventory")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();


                System.out.println(response.body());
        System.out.println("Test results: Test Case 1");
        System.out.println("Test results: "+response.body().prettyPrint());
    }

    @Test (priority = 2)
    public void test_02_CreateOrder()
    {
        String requestBody = "{\n" +
                "    \"id\": 20000,\n" +
                "    \"petId\": 9999,\n" +
                "    \"quantity\": 1,\n" +
                "    \"shipDate\": \"2023-10-15T10:00:00Z\",\n" +
                "    \"status\": \"placed\",\n" +
                "    \"complete\": true\n" +
                "}";


        Response response = given()
                .baseUri(BASE_URL)
                .basePath("/store/order")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(20000))
                .body("petId", equalTo(9999))

                .extract()
                .response();

        orderId = response.path("id");
        System.out.println("Test results: Test Case 2");
        System.out.println("Test results: "+response.body().prettyPrint());
    }

    @Test (priority = 3)
    public void test_03_GetOrderByID()
    {

        Response response = given()
                .baseUri(BASE_URL)
                .basePath("/store/order/"+orderId)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(orderId))
                .extract()
                .response();

        System.out.println("Test results: Test Case 3");
        System.out.println("Test results: "+response.body().prettyPrint());
    }


    @Test (priority = 4)
    public void test_04_GetOrderByID_OrderNotFound()
    {
        int invalidOrderId = 234;


        Response response = given()
                .baseUri(BASE_URL)
                .basePath("/store/order/" + invalidOrderId)
                .when()
                .get()
                .then()
                .statusCode(404)
                .contentType("application/json")
                .body("type", equalTo("error"))
                .body("message", equalTo("Order not found"))
                .extract()
                .response();

        System.out.println("Test results: Test Case 4");
        System.out.println("Test results: "+response.body().prettyPrint());
    }


    @Test (priority = 5)
    public void test_05_DeleteOrder()
    {

        Response response1 = given()
                .baseUri(BASE_URL)
                .basePath("/store/order/" + orderId)
                .when()
                .delete()
                .then()
                .statusCode(200) // Assert status code is 404 (Not Found)
                .extract()
                .response();

        System.out.println("Test results: Test Case 5");
        System.out.println("Test results: "+response1.body().prettyPrint());
    }
}
