package day10;

import POJO.Category;
import POJO.Countries;
import org.junit.jupiter.api.Test;

public class LombokTest {

    @Test
    public void test(){

        Category c1 = new Category("12","abc");
        System.out.println("c1 = " + c1);

        Countries countries = new Countries("AR","ARGENTINA",2);
        System.out.println("countries = " + countries);

    }






}
