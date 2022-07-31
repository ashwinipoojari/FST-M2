package liveproject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssured1 {
    RequestSpecification reqSpec;
    int id;
    @BeforeClass
    public void setup()
    {
        reqSpec = new RequestSpecBuilder()
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization","token")
                .setBaseUri("https://api.github.com")
                .build();
    }

@Test(priority = 1)
public void addService()
{
    String reqBody="{\"title\": \"TestAPIKey\",\"key\":\"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCqbROzpBP4moxgpfz9KfB9WMO4dUWQWeqCyUyFvSkjE6AD4+mn65Bq7jH5+9VcYGbQ0KZC7hcxo/rC/0lywZgF/BNSYvBSgIcI6qjJFBECT8YGpdhFzeGRjeApFFYlgasBJP1omNTk32jgNdINDLDLkCjTC2jNFT/EQqnzccrK8NriGIzU0VoGsybNnYYQ2FktTL9Km75DmStfVh4isDCzVtDUJbXtkelbOcRyo2/I1Hu6M4fwYmQ4Axjk9pp4vsQOV/uiywkDpCCM69DDacUm+jW9odA5kvyBUJvlaQ5NZgftfs1XCDdGjYC3Zr7mTRmSCYd2kifeeQGMMRPJSzc/\"}";
    Response response = given().spec(reqSpec)
            .body(reqBody)
            .when().post("/user/keys");
    System.out.println(response.getBody().asPrettyString());

    id = response.then().extract().path("id");

    response.then().statusCode(201);

}

@Test(priority = 2)
public void getService()
{
    Response response = given().spec(reqSpec)
            .pathParam("keyId",id)
            .when().get("/user/keys/{keyId}");

    System.out.println(response.getBody().asString());
    response.then().statusCode(200);
}

@Test(priority = 3)
public void deleteService()
{
    Response response = given().spec(reqSpec)
            .pathParam("keyId",id)
            .when().delete("/user/keys/{keyId}");


    System.out.println(response.getBody().asString());

    response.then().statusCode(204);
}

}
