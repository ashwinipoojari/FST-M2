package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity3 {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp()
    {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .addHeader("Content-Type","application/json")
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .expectBody("status",equalTo("alive"))
                .build();
    }

    @Test(priority = 1)
    public void addPet()
    {
        String requestBody1 = "{\"id\": 77232, \"name\": \"Riley\", \"status\": \"alive\"}";
        String requestBody2 = "{\"id\": 77233, \"name\": \"Hansel\", \"status\": \"alive\"}";

        Response response1 = given().spec(requestSpec)
                .body(requestBody1).when()
                .post();

        Response response2 = given().spec(requestSpec)
                .body(requestBody2).when().post();

        response1.then().spec(responseSpec);
        response2.then().spec(responseSpec);
    }

    @Test(dataProvider = "petDetails", priority = 2)
    public void getPet(int id, String name, String status)
    {
        Response response = given().spec(requestSpec)
                .pathParam("petId",id)
                .when().get("/{petId}");

        response.then().spec(responseSpec);
        response.then().body("name", equalTo(name));
        response.then().body("status", equalTo(status));
    }

    @DataProvider
    public Object[][] petDetails()
    {
        Object[][] testData = new Object[][] {
                { 77232, "Riley", "alive" },
                { 77233, "Hansel", "alive" }
        };
        return testData;
    }

    @Test(dataProvider = "petDetails", priority = 3)
    public void deletePet(int id, String name, String status)
    {
        Response response = given().spec(requestSpec)
                .pathParam("petId",id)
                .when().delete("/{petId}");

        response.then().body("code",equalTo(200));
    }
}
