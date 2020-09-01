package day09;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ZipDataDriven {

    @ParameterizedTest
    @CsvFileSource( resources = "/state_city.csv", numLinesToSkip = 1 )
    public void testStateCity(String expectedState, String expectedcity, int numberOfZipcodes){

//        System.out.println("state = " + state);
//        System.out.println("city = " + city);
//        System.out.println("numberOfZipcodes = " + numberOfZipcodes);

        Response response = given()
                .pathParam("state", expectedState)
                .pathParam("city", expectedcity)
                .baseUri("http://api.zippopotam.us/us")
                .when()
                .get("/{state}/{city}")
                //.prettyPeek()
                ;
        //.get("/{state}/{city}" , state , city )

        // assert the state and city match in the response
        JsonPath jp = response.jsonPath();

        System.out.println( "state = " + jp.getString("'state abbreviation'") );
        System.out.println( "city = " + jp.getString("'place name'") );

        assertThat(jp.getString("'state abbreviation'") , is(expectedState) );
        assertThat(jp.getString("'place name'") , is(expectedcity) );

        // now we want to count how many item in jsonArray from the response
        // and validate that against our csv file expected numbers

        List<String> zipList = jp.getList("places.'post code'");
        System.out.println("zipList = " + zipList);
        assertThat( zipList , hasSize( numberOfZipcodes ) );





    }


}
