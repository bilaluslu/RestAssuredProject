package day05;

import POJO.Spartan;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SecureSpartanTest {

    //54.160.106.84
    //100.24.235.129
    //23.23.75.140
    // add @BeforeAll and use the spartanApp ID with basic auth
    @BeforeAll
    public static void init(){
        RestAssured.baseURI = "http://23.23.75.140" ;
        RestAssured.port = 8000;
        RestAssured.basePath = "/api" ;
    }
    // add a test
    // make a simple get request /spartans/{id}

    @Test
    public void testGetSingleSpartanSecured(){

        given()
                .log().all()
                .pathParam("id",24)
        .when()
                .get("/spartans/{id}")
                .then()
                .log().all()
                .statusCode(401);

    }

    @DisplayName("Test GET / spartan/{id} Endpoint with credentials")
    @Test
    public void testGettingSpartanWithCredentials(){

        int newID = createRandomSpartan();

        given()
                .log().all()
                .auth().basic("admin","admin")
                .pathParam("id",newID)
        .when()
                .get("/spartans/{id}")
                .then()
                .log().all()
                .statusCode(200);
    }

    public static int createRandomSpartan(){

        String name = new Faker().name().firstName();
        String gender = new Faker().demographic().sex();
        long phone = new Faker().number().numberBetween(1000000000L,9999999999L);

        Spartan sp1 = new Spartan( name ,gender , phone );

        Response response = given()
                            .log().all()
                        .auth().basic("admin","admin")
                            .contentType(ContentType.JSON)
                            .body(sp1)
                         .when()
                            .post("/spartans")
                            .prettyPeek();

        return response.jsonPath().getInt("data.id");
    }























}
