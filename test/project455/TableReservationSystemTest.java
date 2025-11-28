package project455;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;

/**
 *
 * @author Tast
 */
public class TableReservationSystemTest {
    
    // =====================================================
    // ========  Test makeTableReservation  ================
    // =====================================================

    /**
     * Case 1: Standard Success Scenario
     * Verifies that a valid reservation request for an available table works correctly.
     */
    @Test
    public void testMakeTableReservation_Success() {
        System.out.println("TEST: testMakeTableReservation_Success");

        // 1. Setup: Create the system and a list of tables
        TableReservationSystem system = new TableReservationSystem();
        
        // Create table #1, initially available (status = false)
        TableReservation table1 = new TableReservation(1, "01/01/2025", 4, "18:00");
        TableReservation[] tables = { table1 };

        // 2. Simulate User Input: 
        // Table Number: 1
        // Date: 05/20/2025
        // People: 2
        // Time: 19:00
        String simulatedInput = "1\n05/20/2025\n2\n19:00\n";
        
        // Backup original System.in
        InputStream originalIn = System.in;
        // Set System.in to our simulated input
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {
            // 3. Execute
            system.makeTableReservation(tables);    
            
            // 4. Assertions
            // The table should now be reserved (isAvailable returns false)
            boolean isAvailable = tables[0].isAvailable();
            System.out.println("Table 1 Available? " + isAvailable);
            
            assertFalse("Table should be reserved (not available) after successful reservation.", isAvailable);
            
        } finally {
            // Restore original System.in to avoid breaking other tests
            System.setIn(originalIn);
        }
        System.out.println("Result: PASS\n");
    }

    /**
     * Case 2: Already Reserved Scenario
     * Verifies that the system prevents booking a table that is already taken.
     */
    @Test
    public void testMakeTableReservation_AlreadyReserved() {
        System.out.println("TEST: testMakeTableReservation_AlreadyReserved");

        // 1. Setup
        TableReservationSystem system = new TableReservationSystem();
        
        // Create table #2 and manually reserve it first
        TableReservation table2 = new TableReservation(2, "01/01/2025", 4, "18:00");
        table2.reserveTable(); // Set status to true (Reserved)
        
        TableReservation[] tables = { table2 };

        // 2. Simulate User Input attempting to book Table 2
        String simulatedInput = "2\n05/20/2025\n2\n19:00\n";
        
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {
            // 3. Execute
            // The method should print "Table 2 is not available..." and NOT crash
            system.makeTableReservation(tables);
            
            // 4. Assertions
            // The table should remain reserved (available = false)
            boolean isAvailable = tables[0].isAvailable();
            System.out.println("Table 2 Available? " + isAvailable);
            
            assertFalse("Table should remain reserved/unavailable.", isAvailable);
            
        } finally {
            System.setIn(originalIn);
        }
        System.out.println("Result: PASS\n");
    }

    /**
     * Case 3: Non-Existent Table ID Scenario
     * Verifies that entering a table number that doesn't exist (e.g., 99) 
     * does not crash the system or wrongly reserve existing tables.
     */
    @Test
    public void testMakeTableReservation_TableNotFound() {
        System.out.println("TEST: testMakeTableReservation_TableNotFound");

        // 1. Setup: System has only Table 1
        TableReservationSystem system = new TableReservationSystem();
        TableReservation table1 = new TableReservation(1, "01/01/2025", 4, "18:00");
        TableReservation[] tables = { table1 };

        // 2. Input: Try to reserve Table 99 (Does not exist)
        String simulatedInput = "99\n05/20/2025\n2\n19:00\n";
        
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {
            system.makeTableReservation(tables);
            
            // 3. Assertion
            // Since Table 99 doesn't exist, Table 1 should NOT be touched/affected.
            // It should remain available.
            boolean isTable1Available = tables[0].isAvailable();
            System.out.println("Existing Table 1 Available? " + isTable1Available);
            
            assertTrue("Existing tables should not be affected by invalid ID requests.", isTable1Available);
            
        } finally {
            System.setIn(originalIn);
        }
        System.out.println("Result: PASS\n");
    }

    /**
     * Case 4: Invalid Input Type (Exception) Scenario
     * Verifies that the system throws an InputMismatchException if the user 
     * enters text (String) instead of a number for the Table ID.
     */
    @Test(expected = InputMismatchException.class)
    public void testMakeTableReservation_InvalidInputType() {
        System.out.println("TEST: testMakeTableReservation_InvalidInputType");

        TableReservationSystem system = new TableReservationSystem();
        TableReservation table1 = new TableReservation(1, "01/01/2025", 4, "18:00");
        TableReservation[] tables = { table1 };

        // Input: "abc" instead of a number
        String simulatedInput = "abc\n";
        
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {
            system.makeTableReservation(tables);
        } finally {
            // Restore input stream even if exception occurs
            System.setIn(originalIn);
            System.out.println("Result: PASS (Exception Thrown as Expected)\n");
        }
    }
}