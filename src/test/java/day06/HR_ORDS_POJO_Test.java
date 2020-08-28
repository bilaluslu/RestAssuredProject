package day06;

import POJO.Locations;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.*;

import static io.restassured.RestAssured.given;

public class HR_ORDS_POJO_Test {


    @BeforeAll
    public static void init(){

        RestAssured.baseURI = "http://54.174.216.245" ;
        RestAssured.port = 1000 ;
        RestAssured.basePath = "ords/hr";

    }

    @DisplayName("Testing the /location/{location_id} endpoint")
    @Test
    public void testLocation(){

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("location_id", 1700)
                .log().all()
                .when()
                .get("/locations/{location_id}")
                .prettyPeek();

        Locations l1 = response.as(Locations.class);
        System.out.println("l1 = " + l1);
    }

    @DisplayName("Testing the /location endpoint")
    @Test
    public void testLocations(){

        Response response = RestAssured.get("/locations").prettyPeek();

        List<String> addressList = response.jsonPath().getList("items.street_address");
        System.out.println("addressList = " + addressList);

        List<Locations> locationsList = response.jsonPath().getList("items",Locations.class);
        System.out.println("locationsList = " + locationsList);

        for ( Locations eachLocations : locationsList ) {
            System.out.println("eachLocations = " + eachLocations);
        }

        // with Lambda expression
        //locationsList.forEach( eachLocation -> System.out.println("eachLocation = " + eachLocation));

        assertThat(locationsList, hasSize(23) );

        System.out.println();

        // we want to get list of pojo but we only want to get those
// data with country_id as US
        List<Locations> usLocations =
                response.jsonPath().getList("items.findAll { it.country_id=='US' }", Locations.class) ;
//System.out.println("usLocations = " + usLocations);
        usLocations.forEach( eachLocation -> System.out.println(eachLocation));



    }
















}
