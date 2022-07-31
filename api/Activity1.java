package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity1 {

    int id;

    String baseUri = "https://petstore.swagger.io/v2/pet";

    @Test(priority=1)
    public void addPet()
    {
            String requestBody = "{\"id\":19000,\"name\":\"Riley\",\"status\":\"alive\"}";
            Response response = given().header("Content-Type","application/json")
                    .body(requestBody).when().post(baseUri);

            response.then().body("id",equalTo(19000));
            response.then().body("name", equalTo("Riley"));
            response.then().body("status", equalTo("alive"));

            id = response.then().extract().path("id");
    }

    @Test(priority=2)
    public void getPet()
    {
        Response response = given()
                .pathParam("petId",id)
                .when().get(baseUri +"/{petId}");

        response.then().body("id",equalTo(19000));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }

    @Test(priority=3)
    public void deletePet()
    {
        Response response = given()
                .pathParam("petId",id)
                .when().delete(baseUri+"/{petId}");

        response.then().statusCode(200);
        response.then().body("message", equalTo(""+id));
    }
}
