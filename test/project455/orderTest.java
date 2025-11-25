package project455;

import java.util.List;
import java.util.Scanner;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;

public class orderTest {

    // =====================================================
    // ================  Test makeOrder  ===================
    // =====================================================

    @Test
    public void testMakeOrder_AddItemSuccessfully() {

        System.out.println("TEST: testMakeOrder_AddItemSuccessfully");

        Menu menu = new Menu();
        menu.addMainCourse("Burger", "Beef burger", 12.0);

        order orderObj = new order();

        String fakeInput = "Burger\nyes\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(fakeInput.getBytes()));

        order.makeOrder(scanner, menu, orderObj);

        int size = orderObj.getOrderedItems().size();
        String name = orderObj.getOrderedItems().get(0).getName();
        double price = orderObj.getOrderedItems().get(0).getPrice();

        System.out.println("Expected Name: Burger | Found: " + name);
        System.out.println("Expected Price: 12.0 | Found: " + price);

        assertEquals(1, size);
        assertEquals("Burger", name);
        assertEquals(12.0, price, 0.001);

        System.out.println("Result: PASS\n");
    }



    // =====================================================
    // =========  Test calculateTotalPrice  ================
    // =====================================================

    @Test
    public void testCalculateTotalPrice_MultipleItems() {

        System.out.println("TEST: testCalculateTotalPrice_MultipleItems");

        order orderObj = new order();

        orderObj.addItem(new MenuItem("Salad", "Fresh", 5.0));
        orderObj.addItem(new MenuItem("Burger", "Beef", 12.0));
        orderObj.addItem(new MenuItem("Cake", "Chocolate", 7.0));

        double total = orderObj.calculateTotalPrice();

        System.out.println("Expected Total: 24.0 | Found: " + total);

        assertEquals(24.0, total, 0.001);

        System.out.println("Result: PASS\n");
    }

    @Test
    public void testCalculateTotalPrice_EmptyOrder() {

        System.out.println("TEST: testCalculateTotalPrice_EmptyOrder");

        order orderObj = new order();

        double total = orderObj.calculateTotalPrice();

        System.out.println("Expected Total: 0.0 | Found: " + total);

        assertEquals(0.0, total, 0.001);

        System.out.println("Result: PASS\n");
    }
}
