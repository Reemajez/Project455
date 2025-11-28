
package project455;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)


public class RestaurantComponentTest {

    @Test
    public void test01_FullOrderToPaymentCycle() {
        System.out.println("Component Test 1: Full Order -> Payment Cycle");
        Menu menu = new Menu();
        order customerOrder = new order();

        MenuItem item1 = menu.findMenuItemsByName("Caesar_Salad");
        customerOrder.addItem(item1);
        MenuItem item2 = menu.findMenuItemsByName("Grilled_Salmon");
        customerOrder.addItem(item2);

        assertEquals(2, customerOrder.getOrderedItems().size());
        
        customerOrder.applyDiscount(50.0); 
        
        double paymentAmount = 20.00;
        double actualChange = Payment.makePayment(customerOrder, paymentAmount);

        assertEquals(6.80, actualChange, 0.01);
        System.out.println("Cycle Passed Successfully!\n");
    }

    @Test
    public void test02_ReservationFlow() {
        System.out.println("Component Test 2: Reservation System Flow");

        TableReservationSystem resSystem = new TableReservationSystem();
        TableReservation table1 = new TableReservation(1, "01/01/2025", 4, "18:00");
        TableReservation[] tables = { table1 };

        assertTrue(table1.isAvailable());

        String simulatedUserInput = "1\n05/20/2025\n4\n19:00\n";
        // UPDATED: Create scanner directly
        Scanner sc = new Scanner(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        // UPDATED: Pass scanner
        resSystem.makeTableReservation(sc, tables);

        assertFalse("Table 1 should now be reserved", table1.isAvailable());
        System.out.println("Reservation Flow Passed Successfully!\n");
    }
    
    @Test
    public void test03_OrderNonExistentItem() {
        System.out.println("Component Test 3: Ordering Non-Existent Item");
        Menu menu = new Menu();
        order customerOrder = new order();
        MenuItem item = menu.findMenuItemsByName("Unicorn_Steak");
        assertNull(item);
        if (item != null) customerOrder.addItem(item);
        assertEquals(0, customerOrder.getOrderedItems().size());
        System.out.println("Non-Existent Item Test Passed!\n");
    }

    @Test
    public void test04_InsufficientPaymentFlow() {
        System.out.println("Component Test 4: Insufficient Payment Flow");
        Menu menu = new Menu();
        order customerOrder = new order();
        
        customerOrder.addItem(menu.findMenuItemsByName("Grilled_Salmon")); 
        customerOrder.addItem(menu.findMenuItemsByName("Mozzarella_Sticks")); 
        
        double paymentAmount = 10.0; 
        double result = Payment.makePayment(customerOrder, paymentAmount);

        assertTrue("Payment should fail", Double.isNaN(result));
        System.out.println("Insufficient Payment Test Passed!\n");
    }

    @Test
    public void test05_DoubleReservationFlow() {
        System.out.println("Component Test 5: Double Booking Flow");

        TableReservationSystem resSystem = new TableReservationSystem();
        TableReservation table1 = new TableReservation(1, "01/01/2025", 4, "18:00");
        TableReservation[] tables = { table1 };

        // 1. First Reservation
        String firstUser = "1\n01/01/2025\n4\n18:00\n";
        Scanner sc1 = new Scanner(new ByteArrayInputStream(firstUser.getBytes()));
        resSystem.makeTableReservation(sc1, tables);
        assertFalse(table1.isAvailable());

        // 2. Second Reservation
        String secondUser = "1\n01/01/2025\n4\n18:00\n";
        Scanner sc2 = new Scanner(new ByteArrayInputStream(secondUser.getBytes()));
        resSystem.makeTableReservation(sc2, tables); 

        assertFalse(table1.isAvailable());
        System.out.println("Double Booking Test Passed!\n");
    }
}