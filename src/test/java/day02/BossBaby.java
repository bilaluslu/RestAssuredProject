package day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class BossBaby {

    @BeforeAll
    public static void setUp(){

        RestAssured.baseURI = "http://www.omdbapi.com" ;
        //RestAssured.basePath = "/api" ;
    }

    @DisplayName("Testing/Movie")
    @Test
    public void testMovie () {

        RestAssured.given()
                .log().all()
                .queryParam("apikey","be8cd0d1")
                .queryParam("t","Boss")
                .queryParam("plot","full")
                .when()
                //.get("/spartans/search")
                .then()
                .log().all()
                .statusCode(200)
                .body("Content-Type", is("application/json; charset=utf-8"))
                .body("Year", Matchers.equalTo("2011–2012"))
                .body("Ratings[0].Value",is(8.1/10))
                .body("Title",is("Boss"))
                .body("Released",is("N/A"))
                .body("Runtime",is("56 min"))
                .body("Genre",is("Crime, Drama"))
                .body("Director",is("N/A"))
                .body("Actors",is("Kelsey Grammer, Connie Nielsen, Hannah Ware, Jeff Hephner"))
                .body("Writer",containsString("Farhad Safinia"));


    }
//"Title": "Boss",
//    "Year": "2011–2012",
//    "Rated": "TV-MA",
//    "Released": "N/A",
//    "Runtime": "56 min",
//    "Genre": "Crime, Drama",
//    "Director": "N/A",
//    "Writer": "Farhad Safinia",
//    "Actors": "Kelsey Grammer, Connie Nielsen, Hannah Ware, Jeff Hephner",





















}
