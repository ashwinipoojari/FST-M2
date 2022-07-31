package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {

    String username;
    String baseUri = "https://petstore.swagger.io/v2/user";

    @Test(priority = 1)
    public void addUser() throws IOException {
        File inputFile = new File("src/test/java/activities/input.json");
        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] bytes = new byte[(int) inputFile.length()];
        inputStream.read(bytes);
        String requestBody = new String(bytes, "UTF-8");

        Response response = given().header("Content-Type","application/json")
                .body(requestBody)
                .when().post(baseUri);

        inputStream.close();
    }

    @Test(priority = 2)
    public void getUser() throws IOException {
        Response response = given()
                .pathParam("username","hailey")
                .when().get(baseUri+"/{username}");

        response.then().body("id",equalTo(1805));
        response.then().body("username",equalTo("hailey"));
        response.then().body("firstName", equalTo("Justin"));
        response.then().body("lastName", equalTo("Case"));
        response.then().body("email", equalTo("justincase@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9812763450"));

        File responseFile = new File("src/test/java/activities/response.log");
        responseFile.createNewFile();
        FileWriter writer= new FileWriter(responseFile.getPath());
        writer.write(response.asPrettyString());
        writer.close();

    }

    @Test(priority = 3)
    public void deleteUser()
    {
        Response response = given()
                .pathParam("username","hailey")
                .when().delete(baseUri+"/{username}");

        response.then().statusCode(200);
        response.then().body("message", equalTo("hailey"));
    }
}
