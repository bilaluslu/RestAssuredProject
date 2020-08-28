package day06;

import com.sun.xml.bind.v2.TODO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LibraryAppTest {

    private static String libraryToken;

    @BeforeAll
    public static void init(){

        //TODO challenge yourself to move all the hardcoded data into configuration file
        RestAssured.baseURI = "http://library1.cybertekschool.com" ;
        RestAssured.basePath = "/rest/v1" ;
        libraryToken = loginAndGetToken("librarian69@library", "KNPXrm3S");
    }

    @DisplayName("Send a request to /DashboardStat")
    @Test
    public void testDashboardStatsWithToken (){

        given()
                .log().all()
                .header("x-library-token",libraryToken)
        .when()
                .get("/dashboard_Stats")
        .then()
                .log().all()
                .statusCode(200)
                .body("book_count",is("985"))
                .body("borrowed_books",is("600"))
                .body("users",is("5042"))
                .body("image", is( nullValue() ) ) // this is how to use matcher for null value
        ;

    }
    // add a test for the POST/ decode endpoint
    // this endpoint does not need authorization
    // it accept from param as name token value your long token
    // and return json response as user information and authority
    // assert the email of user is same as the email you used the token

    // LOGIN AND DECODE REQUEST DOES NOT NEED ANY AUTHENTICATION
    // LOGIN --->> GENERATE TOKEN
    // DECODE --->> TAKE A TOKEN AND DECODE ITS INFO
    // WE DO NOT NEED TO ADD HEADER YOUR LONG TOKEN TO AUTHORIZE THIS REQUEST

    @Test
    public void testDecodeJWT_Token(){
        given()
                .log().all()
                .accept(ContentType.JSON)   // tis is telling , i eant to json back as response
                .contentType(ContentType.URLENC)    // this specify what kinda of data you are sending to the server in the body
                .formParam("token",libraryToken)
        .when()
                .post("/decode")
        .then()
                .statusCode( is (200) )
        // assert the email is librarian69@library because we used this email to get the token
                .body("email",is("librarian69@library"))
                .body("token", is(libraryToken))
        ;
    }

    @DisplayName("Test / get_user_by_id/{id} Endpoint")
    @Test
    public void testSingleUserDate(){

        given()
                .log().all()
                .header("x-library-token" , libraryToken)
                .pathParam("id",2080)
        .when()
                .get("/get_user_by_id/{id}")
        .then()
                .log().all()
                .statusCode(200)
        .body("id",is("2080"))
        .body("full_name",is("Test Student 142"))
        .body("email", is("student142@library"))
                ;




    }























    /**
     * A static utility method to get the token by providing username and password
     * as Post request to /Login endpoint and capture the token field from response json
     * @param username
     * @param password
     * @return the token as String from the response json
     */
    public static String loginAndGetToken(String username, String password){

        String token = "";

        Response response = given()
//                                .log().all()
                // explicitly saying the body content type is x-www-urlencoded-form-data
                .contentType(ContentType.URLENC)
                .formParam("email",username)
                .formParam("password", password ).
                        when()
                .post("/login") ;

        //token = response.path("token") ;  // this is using path method
        token = response.jsonPath().getString("token") ;
        return token ;
    }



























}
