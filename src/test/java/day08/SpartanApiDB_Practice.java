package day08;

import Utility.ConfigurationReader;
import Utility.DB_Utility;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpartanApiDB_Practice {


    /**
     * The dev just implemented the search endpoint
     * and want it to be tested to make sure it's actually
     * returning the correct result from the database
     *
     *    GET /spartans/search?gender=Female&nameContains=a
     *  we want to test the count of result is correct
     *  for numberOfElements field.
     *
     *  The Database query to get the count is :
     *  // all the females that have a in their name , case insensitive
     *   -- This is how we get the data with case insensitive manner
     *      SELECT * FROM SPARTANS
     *       WHERE LOWER(gender) = 'female'
     *       and LOWER(name) LIKE '%a%' ;
     *
     */
    @BeforeAll
    public static void init(){

        RestAssured.baseURI = "http://100.25.192.231" ;
        RestAssured.port = 8000;
        RestAssured.basePath = "/api" ;

        //DB_Utility.createConnection("spartan1");
        DB_Utility.createConnection(ConfigurationReader.getProperty("spartan1.database.url"),
                                     ConfigurationReader.getProperty("spartan1.database.username"),
                                      ConfigurationReader.getProperty("spartan1.database.password"));
    }

    @DisplayName("Testing out my DB Connection")
    @Test
    public void testDB(){
        //DB_Utility.runQuery("SELECT * FROM SPARTANS");
        //DB_Utility.displayAllData();

        String query = "SELECT * FROM SPARTANS" +
                "       WHERE LOWER(gender) = 'female'" +
                "       and LOWER(name) LIKE '%a%'";

        DB_Utility.runQuery( query );
        DB_Utility.displayAllData();

        int expectedResult = DB_Utility.getRowCount();
        System.out.println("expectedResult = " + expectedResult);

    }



    @DisplayName("Testing /spartans/search Endpoint and Validate against DB")
    @Test
    public void testSearch(){

        // make a request to GET /spartans/search
        // using query parameter gender Female  nameContains a

        Response response = given()
                .log().all()
                .queryParam("gender","Female")
                .queryParam("nameContains", "a").
                        when()
                .get("/spartans/search")
                .prettyPeek();

        int resultCount =  response.path("numberOfElements") ;
        System.out.println("resultCount = " + resultCount);

        String query = "SELECT * FROM SPARTANS" +
                "       WHERE LOWER(gender) = 'female'" +
                "       and LOWER(name) LIKE '%a%'";

        DB_Utility.runQuery( query );
        DB_Utility.displayAllData();

        int expectedResult = DB_Utility.getRowCount();
        System.out.println("expectedResult = " + expectedResult);

        assertEquals(expectedResult,resultCount);

    }


    @DisplayName("Testing /spartans/search Endpoint and Validate against DB")
    @Test
    public void test(){

        // make a request to GET /spartans/search
        // using query parameter gender Female  nameContains a

        Response response = given()
                .log().all()
                .queryParam("gender","Female")
                .queryParam("nameContains", "a").
                        when()
                .get("/spartans/search")
                .prettyPeek();
        // We want to store the id list as List<String> rather than List of Integer so we can compare easily
        // with the List<String> we got from DB Response , and no parsing needed
        // so we used the getList method that accept 2 parameters
        // the jsonpath to get the list and the Data type of the List you want ! -->> String.class
        List<String> idListFromResponse = response.jsonPath().getList("content.id" , String.class) ;

        String query = "SELECT * FROM SPARTANS     " +
                " WHERE LOWER(gender) = 'female'  " +
                " and LOWER(name) LIKE '%a%' ";
        DB_Utility.runQuery(query);

        List<String> idListFromDB =  DB_Utility.getColumnDataAsList(1) ;

        assertEquals( idListFromResponse.size() , idListFromDB.size() ) ;

        // how to assert 2 list have same content
        assertEquals(idListFromDB, idListFromResponse);



    }




    @AfterAll
    public static void destroy(){

        RestAssured.reset();
        DB_Utility.destroy();

    }






























}
