package project455;

import java.util.List;
import java.util.Scanner;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MenuTest {

    private Menu menu;

    @Before
    public void setUp() {
        menu = new Menu();
        menu.addAppetizer("Salad", "Fresh salad", 5.0);
        menu.addMainCourse("Burger", "Beef burger", 12.0);
        menu.addDessert("Cake", "Chocolate cake", 7.0);
    }

    @Test
    public void testFindMenuItemsByName_Exists() {
        MenuItem result = menu.findMenuItemsByName("Burger");

        System.out.println("TEST: testFindMenuItemsByName_Exists");
        System.out.println("Expected: Burger | Found: " + (result != null ? result.getName() : "null"));

        assertNotNull(result);
        assertEquals("Burger", result.getName());

        System.out.println("Result: PASS\n");
    }

    @Test
    public void testFindMenuItemsByName_NotFound() {
        MenuItem result = menu.findMenuItemsByName("Pizza");

        System.out.println("TEST: testFindMenuItemsByName_NotFound");
        System.out.println("Expected: null | Found: " + result);

        assertNull(result);

        System.out.println("Result: PASS\n");
    }

    @Test
    public void testFindMenuItemsByName_CaseInsensitive() {
        MenuItem result = menu.findMenuItemsByName("cAkE");

        System.out.println("TEST: testFindMenuItemsByName_CaseInsensitive");
        System.out.println("Expected: Cake | Found: " + (result != null ? result.getName() : "null"));

        assertNotNull(result);
        assertEquals("Cake", result.getName());

        System.out.println("Result: PASS\n");
    }
}
