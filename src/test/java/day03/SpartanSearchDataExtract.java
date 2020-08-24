package day03;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

/*
    Add all the imports
		add @BeforeAll to initilize the baseURI, basePath
		Add a test
		send get request to /spartan/search
		query paramters gender = Female
		save the response Object into response variable
		call jsonPath method to get JsonPath object from respone
		JsonPath jp = response.jsonPath() ;
		// get the list of all IDs store it into List
		// get the list of all names store it into List of String
		// get the mumberOfElements field value
     */
public class SpartanSearchDataExtract {

    @BeforeAll
    public static void init(){
        RestAssured.baseURI = "http://34.224.4.31";
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";
    }

    @Test
    public void test1(){
        Response response = RestAssured.given()
                .log().all()    // just to see my request in case anything goes wrong
                .queryParam("gender", "Female")
                .when().get("/spartans/search")
                //.prettyPeek()
                ;

        System.out.println("=====================================================");
        JsonPath jp = response.jsonPath();

        int numberOfFemaleSpartans = jp.getInt("numberOfElements");
        System.out.println("numberOfFemaleSpartans = " + numberOfFemaleSpartans);

        // if you want to get single Spartan, for example the first id
        // you would use jsonPath of content[0].id
        // if you want to get all id's, you can use getList method and remove the index
            // content.id for the id, content.name

        // storing all id's into list of Integer
        List<Integer> numList = jp.getList("content.id");
        System.out.println("numList = " + numList);

        List<String> nameList = jp.getList("content.name");
        System.out.println("nameList = " + nameList);





    }












}
