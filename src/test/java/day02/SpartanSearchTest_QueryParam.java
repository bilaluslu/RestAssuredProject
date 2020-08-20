package day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

//http://34.224.4.31:8000/api/spartans/search?gender=male&nameContains=ea
public class SpartanSearchTest_QueryParam {

    @BeforeAll
    public static void setUp(){

        RestAssured.baseURI = "http://34.224.4.31:8000" ;
        RestAssured.basePath = "/api" ;
    }

    @DisplayName("Testing/spartans/search_Endpoint")
    @Test
    public void testSearch () {

        RestAssured.given()
                .log().all()
                .queryParam("gender","Male")
                .queryParam("nameContains","j")
        .when()
                .get("/spartans/search")
        .then()
                .log().all()
                .statusCode(200);
    }





































}
