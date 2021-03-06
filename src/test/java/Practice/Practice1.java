package Practice;


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


public class Practice1 {

    @BeforeAll
    public static void init(){
        RestAssured.baseURI = "http://100.25.192.231";
        RestAssured.port = 8000;
        //RestAssured.basePath = "/api";
    }

    @DisplayName("Testing GET /api/spartans")
    @Test
    public void testGetSpartan() {

        given()
                .contentType(ContentType.JSON)
                .log().all()
                .pathParam("id", 237)

                .when()

                .get("api/spartans/{id}")
                .then()
                .log().all()
                .statusCode( is(200) )
                .body("name",is("Ismael") )
                .body("gender", is("Male") )
                .body("phone", is(2122221112));





    }

    @DisplayName("Testing POST /api/spartans")
    @Test
    public void testPostSpartan(){

        Map<String,Object> postEmrah = new LinkedHashMap<>();
        postEmrah.put("name","New1");
        postEmrah.put("gender","Male");
        postEmrah.put("phone",1236547893);

        given().contentType(ContentType.JSON)
                .log().all()
                .body( postEmrah )
        .when()
                .post("api/spartans")
        .then()
                .log().all();

    }

    @DisplayName("Testing PUT /api/spartans")
    @Test
    public void testPutSpartan(){

        Map<String,Object> putMap = new LinkedHashMap<>();
        putMap.put("name","New Name");
        putMap.put("gender","Male");
        putMap.put("phone",1236548520);

        for (int i = 0; i < 500 ; i++) {


            given().contentType(ContentType.JSON)
                    .pathParam("id", i)
                    .log().all()
                    .body(putMap)
                    .when()
                    .put("/api/spartans/{id}")
                    .then()
                    .log().all()
                    ;

        }

    }









}
