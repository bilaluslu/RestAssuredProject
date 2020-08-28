package day06;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.get;

public class HR_ORDS_GroovyMagic {

    @BeforeAll
    public static void init(){
        RestAssured.baseURI = "http://54.174.216.245" ;
        RestAssured.port = 1000 ;
        RestAssured.basePath = "ords/hr";
    }
    @DisplayName("Testing the /Employees endpoint")
    @Test
    public void testEmployees(){

        Response response = get("/employees");

        JsonPath jp = response.jsonPath();

        // print all the ID by getting a list
        System.out.println("All IDs : " + jp.getList("items.employee_id"));

        // print first ID and last ID
        System.out.println("first ID : " + jp.getInt("items[0].employee_id"));
        System.out.println("last ID : " + jp.getInt("items[-1].employee_id"));

        // get all the ids from first one till fifth
        System.out.println("from the first till the fifth : " + jp.getList("items[0..4].employee_id"));

        // get all last names from index 10-15
        System.out.println("last name from 10-15 : " + jp.getList("items[10..15].last_name"));

        // get the employee first_name with employee id of 105
        // find and find all where you can specify the criteria to restrict the result
        // find method will return single value that fall into the criteria compared to findAll will return a list
        // find { it.employee_id == 105 }
        String result = jp.getString("items.find{ it.employee_id == 105}.last_name ");
        System.out.println("result = " + result);

        // using above examples : find the salary of employee with email value LDEHAAN
        int salary = jp.getInt("items.find{ it.email=='LDEHAAN'}.salary ");
        System.out.println("salary = " + salary);

        // finaAll will get all the result that match the criteria and return it as a list
        // save all the first_names of the employees with salary more than 15000
        List<String> firstNames = jp.getList("items.findAll{ it.salary > 15000 }.first_name");
        System.out.println("first names = " + firstNames);

        // find out all the phone_number in department_id 90
        List<String> phoneNumbersDep90 = jp.getList("items.findAll{ it.department_id == 90 }.phone_number");
        System.out.println(  "Departments 90s' phoneNumbers = " + phoneNumbersDep90  );

    }









}
