package project455;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class FullSystemTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();

    @Test
    public void test01_testFullCustomerJourney() {
        System.out.println("SYSTEM TEST: Test 1 Starting Full Customer Journey...");

        // ---------------------------------------------------------
        // 1. Inputs: (1: View Menu) -> (2: Order) -> (3: Reserve) -> (4: Payment) -> (5: Exit)
        // ---------------------------------------------------------
        String simulatedInput = 
            // Step 1: View Menu
            "1\n" + 
            // Step 2: Make Order (Caesar_Salad)
            "2\n" + 
            "Caesar_Salad\n" + 
            "yes\n" + 
            // Step 3: Reserve Table
            "3\n" + 
            "1\n" + 
            "01/01/2025\n" + 
            "4\n" + 
            "18:00\n" + 
            // Step 4: Make Payment (Total ~13.20$. Pay 20.00$)
            "4\n" + 
            "no\n" +          // Do you have a discount coupon? -> no
            "20.00\n" +       // Enter payment amount
            // Step 5: Exit
            "5\n";            

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        System.setOut(new PrintStream(capturedOut));

        try {
            Project455.main(new String[]{});

            String output = capturedOut.toString();

            assertTrue("System should display menu items", output.contains("Grilled_Salmon"));
            assertTrue("System should confirm order", output.contains("Ordered: Caesar_Salad"));
            assertTrue("System should confirm table reservation", output.contains("Table reserved!"));
            assertTrue("System should confirm successful payment", output.contains("Payment successful!"));
            assertTrue("System should print exit message", output.contains("Thank you!"));

        } catch (Exception e) {
            fail("Bug: System crashed during Full Customer Journey: " + e.getMessage());
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        
        System.out.println("SYSTEM TEST: Passed Successfully.\n");
    }

    // =========================================================================
    // ==================  NEGATIVE SYSTEM TESTS (Failure Scenarios) =============
    // =========================================================================
    
    @Test
    public void test02_testInsufficientPaymentFlow() {
        System.out.println("SYSTEM TEST: Test 2 Starting Insufficient Payment Flow...");

        // Inputs: (2: Order) -> (4: Payment) -> (5: Exit)
        String simulatedInput = 
            // Step 1: Make Order (Caesar_Salad = 12.00$)
            "2\n" + 
            "Caesar_Salad\n" + 
            "yes\n" + 
            // Step 2: Make Payment (Total Due is 13.20$. Pay 5.00$)
            "4\n" + 
            "no\n" + 
            "5.00\n" +       // Deliberately insufficient payment
            // Step 3: Exit
            "5\n";            

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        System.setOut(new PrintStream(capturedOut));
        
        try {
            Project455.main(new String[]{});
            
            String output = capturedOut.toString();
            
            assertTrue("System should report insufficient payment amount.", output.contains("Insufficient payment amount. Payment failed."));
            assertTrue("System should exit gracefully.", output.contains("Thank you!"));

        } catch (Exception e) {
            fail("Bug: System crashed during insufficient payment flow: " + e.getMessage());
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        
        System.out.println("SYSTEM TEST: Insufficient Payment Flow Passed.\n");
    }

    @Test(expected = InputMismatchException.class)
    public void test03_testInvalidMainMenuInput_Crash() throws Exception {
        System.out.println("SYSTEM TEST: Test 3 Invalid Main Menu Input (Expected Crash)...");
        
        String simulatedInput = 
            "A\n" + 
            "5\n"; 

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        
        try {
            Project455.main(new String[]{});
        } finally {
            System.setIn(originalIn);
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void test04_testInvalidOrderInput_Crash_Bug() throws Exception {
        System.out.println("\nSYSTEM TEST: Test 4 Invalid Order Item Input (Expected Crash/Bug)...");

        // Inputs: (2: Order) 
        String simulatedInput = 
            "2\n" +             // Select Option 2 (Order)
            "NonExistentItem\n" + // Item that will throw IllegalArgumentException in order.makeOrder
            "5\n";              // Select Option 5 (Exit - may not be reached)

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        
        try {
            Project455.main(new String[]{});
        } finally {
            System.setIn(originalIn);
        }

    }
}