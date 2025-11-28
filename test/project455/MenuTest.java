package project455;

import java.util.List;
import java.util.Scanner;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

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
    public void test01_FindMenuItemsByName_Exists() {
        MenuItem result = menu.findMenuItemsByName("Burger");

        System.out.println("TEST: test01_FindMenuItemsByName_Exists");
        System.out.println("Expected: Burger | Found: " + (result != null ? result.getName() : "null"));

        assertNotNull(result);
        assertEquals("Burger", result.getName());

        System.out.println("Result: PASS\n");
    }

    @Test
    public void test02_FindMenuItemsByName_NotFound() {
        MenuItem result = menu.findMenuItemsByName("Pizza");

        System.out.println("TEST: test02_FindMenuItemsByName_NotFound");
        System.out.println("Expected: null | Found: " + result);

        assertNull(result);

        System.out.println("Result: PASS\n");
    }

    @Test
    public void test03_FindMenuItemsByName_CaseInsensitive() {
        MenuItem result = menu.findMenuItemsByName("cAkE");

        System.out.println("TEST: test03_FindMenuItemsByName_CaseInsensitive");
        System.out.println("Expected: Cake | Found: " + (result != null ? result.getName() : "null"));

        assertNotNull(result);
        assertEquals("Cake", result.getName());

        System.out.println("Result: PASS\n");
    }

   
    
    @Test
    public void test04_FindMenuItemsByName_EmptyString_ShouldFail() {
        System.out.println("TEST: test04_FindMenuItemsByName_EmptyString_ShouldFail");

        try {
            MenuItem result = menu.findMenuItemsByName("");
            assertNull("Expected null for empty input, bug detected!", result);
            System.out.println("Result: FAIL, System should handle empty input\n");
        } catch (Exception e) {
            System.out.println("Exception thrown (Correct): " + e);
        }
    }

    // =====================================================
    // ============  Test Cases that MUST FAIL (Bugs)  =====
    // =====================================================
    
    @Test
    public void test05_FindMenuItemsByName_Whitespace_Bug() {
        System.out.println("TEST: test05_FindMenuItemsByName_Whitespace_Bug");

        MenuItem result = menu.findMenuItemsByName("Burger ");

        assertNotNull("FAIL: Bug Confirmed: System failed to find item due to trailing whitespace!", result);

        System.out.println("Result: FAIL, System is sensitive to whitespace in item query\n");
    }

    @Test
    public void test06_FindMenuItemsByName_NullInput_Bug() {
        System.out.println("TEST: test06_FindMenuItemsByName_NullInput_Bug");

        try {
            menu.findMenuItemsByName(null);

            fail("FAIL: Bug Confirmed: System crashed with NullPointerException for null input! Expected safe handling/return null.");
        } catch (NullPointerException e) {
            fail("FAIL: Bug Confirmed: System crashed with NullPointerException for null input! Expected safe handling/return null.");
        } catch (Exception e) {
            System.out.println("Exception thrown (Expected Bug): " + e.getClass().getName() + "\n");
        }
    }

}
