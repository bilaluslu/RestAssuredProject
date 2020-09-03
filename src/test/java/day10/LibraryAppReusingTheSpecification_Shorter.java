package day10;

import POJO.Category;
import POJO.User;
import Utility.ConfigurationReader;
import Utility.LibraryUtil;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;


public class LibraryAppReusingTheSpecification_Shorter {



    @BeforeAll
    public static void init(){

        RestAssured.baseURI = ConfigurationReader.getProperty("library1.base_url");
        RestAssured.basePath = "/rest/v1" ;

        String theToken = LibraryUtil.loginAndGetToken("librarian69@library","KNPXrm3S");
        // just like we set the baseURI and basePath
        // we are using the static field of RestAssured to set it at global level
        RestAssured.requestSpecification  = given().accept(ContentType.JSON)  // we want json back
                .log().all()               // we want to log all
                .header("x-library-token", theToken) ; // we want to set the token header
        // we are using the static field of RestAssured to set it at global level
        RestAssured.responseSpecification =  expect().statusCode(200)       // expecting the Response status code 200
                .contentType(ContentType.JSON)  // contentType is json
                .logDetail(LogDetail.ALL) ;     // want to log all of them

    }

    @DisplayName("Testing GET /get_book_categories Endpoint with spec")
    @Test
    public void testLibrary(){

        Response response = when()
                .get("/get_book_categories");
        List<Category> categoryList = response.jsonPath().getList("");
        System.out.println("categoryList = " + categoryList);

        // above code is great, but what if I wanted to store each category as map rather than POJO
        // each category is key value pair --> Map
        // and we have many category --> List of Map

        // jsonPath methods always try to help to convert the types where it can
        // in this case, each category in jsonArray we tried to store into Map then get a list out of it
        // and Jackson databind take care of all conversion

        List< Map<String,String> > categoryMapList = response.jsonPath().getList("");
        System.out.println("categoryMapList = " + categoryMapList);

    }

    @DisplayName("Testing GET /get_all_users Endpoint with spec")
    @Test
    public void testGetAllUsers(){


        Response response = when().get(" /get_all_users");
        JsonPath jp = response.jsonPath();
        List<User> allUserList = jp.getList("", User.class);
        System.out.println("allUserList = " + allUserList);

    }

    // get the Map<String,String> object out of the response of GET / dashboard_statss
    @DisplayName("Testing GET /dashboard_stats Endpoint with spec")
    @Test
    public void testGet_Dashboard_stats(){


        Response response = when()
                .get(" /dashboard_stats").prettyPeek();

        // if there is no path needed to get to what you are looking for
        // or if you wanted to point to your entire response, you can just provide

        Map<String,Object> statMap = response.jsonPath().getMap("");
        System.out.println("statMap = " + statMap);

    }







}
