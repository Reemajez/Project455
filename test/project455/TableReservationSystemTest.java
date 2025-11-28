package project455;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.InputMismatchException;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)


public class TableReservationSystemTest {
    
    @Test
    public void test01_MakeTableReservation_Success() {
        System.out.println("TEST 1: testMakeTableReservation_Success");

        TableReservationSystem system = new TableReservationSystem();
        TableReservation table1 = new TableReservation(1, "01/01/2025", 4, "18:00");
        TableReservation[] tables = { table1 };

        String simulatedInput = "1\n05/20/2025\n2\n19:00\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        system.makeTableReservation(sc, tables);    
            
        boolean isAvailable = tables[0].isAvailable();
        System.out.println("Table 1 Available? " + isAvailable);
            
        assertFalse("Table should be reserved", isAvailable);
        System.out.println("Result: PASS\n");
    }

    @Test
    public void test02_MakeTableReservation_AlreadyReserved() {
        System.out.println("TEST 2: testMakeTableReservation_AlreadyReserved");

        TableReservationSystem system = new TableReservationSystem();
        TableReservation table2 = new TableReservation(2, "01/01/2025", 4, "18:00");
        table2.reserveTable(); 
        TableReservation[] tables = { table2 };

        String simulatedInput = "2\n05/20/2025\n2\n19:00\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        system.makeTableReservation(sc, tables);
            
        boolean isAvailable = tables[0].isAvailable();
        assertFalse("Table should remain reserved", isAvailable);
        System.out.println("Result: PASS\n");
    }

    @Test
    public void test03_MakeTableReservation_TableNotFound() {
        System.out.println("TEST 3: testMakeTableReservation_TableNotFound");

        TableReservationSystem system = new TableReservationSystem();
        TableReservation table1 = new TableReservation(1, "01/01/2025", 4, "18:00");
        TableReservation[] tables = { table1 };

        String simulatedInput = "99\n05/20/2025\n2\n19:00\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        system.makeTableReservation(sc, tables);
            
        boolean isTable1Available = tables[0].isAvailable();
        assertTrue("Table 1 should remain available", isTable1Available);
        System.out.println("Result: PASS\n");
    }

    @Test(expected = InputMismatchException.class)
    public void test04_MakeTableReservation_InvalidInputType() {
        System.out.println("TEST 4: testMakeTableReservation_InvalidInputType");

        TableReservationSystem system = new TableReservationSystem();
        TableReservation table1 = new TableReservation(1, "01/01/2025", 4, "18:00");
        TableReservation[] tables = { table1 };

        String simulatedInput = "abc\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        system.makeTableReservation(sc, tables);
    }
}