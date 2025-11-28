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
    
    // =====================================================
    // ============  Test applyDiscount  ===================
    // =====================================================

    @Test
    public void testApplyDiscount_NormalValid() {
        System.out.println("TEST: testApplyDiscount_NormalValid");
        
        order orderObj = new order();
        double input = 10.0;
        
        orderObj.applyDiscount(input);
        
        System.out.println("Expected Discount: 10.0 | Found: " + orderObj.getDiscountPercentage());
        assertEquals("Valid discount of 10% should be stored.", 10.0, orderObj.getDiscountPercentage(), 0.001);
        System.out.println("Result: PASS\n");
    }

    @Test
    public void testApplyDiscount_LowerBoundary() {
        System.out.println("TEST: testApplyDiscount_LowerBoundary");
        
        order orderObj = new order();
        double input = 0.0;
        
        orderObj.applyDiscount(input);
        
        System.out.println("Expected Discount: 0.0 | Found: " + orderObj.getDiscountPercentage());
        assertEquals("0% discount should be valid.", 0.0, orderObj.getDiscountPercentage(), 0.001);
        System.out.println("Result: PASS\n");
    }

    @Test
    public void testApplyDiscount_UpperBoundary() {
        System.out.println("TEST: testApplyDiscount_UpperBoundary");
        
        order orderObj = new order();
        double input = 100.0;
        
        orderObj.applyDiscount(input);
        
        System.out.println("Expected Discount: 100.0 | Found: " + orderObj.getDiscountPercentage());
        assertEquals("100% discount should be valid.", 100.0, orderObj.getDiscountPercentage(), 0.001);
        System.out.println("Result: PASS\n");
    }

    @Test
    public void testApplyDiscount_InvalidNegative() {
        System.out.println("TEST: testApplyDiscount_InvalidNegative");
        
        order orderObj = new order();
        double input = -5.0;
        
        orderObj.applyDiscount(input);
        
        System.out.println("Expected Discount: 0.0 (unchanged) | Found: " + orderObj.getDiscountPercentage());
        assertEquals("Negative discount should be rejected.", 0.0, orderObj.getDiscountPercentage(), 0.001);
        System.out.println("Result: PASS\n");
    }

    @Test
    public void testApplyDiscount_InvalidHigh() {
        System.out.println("TEST: testApplyDiscount_InvalidHigh");
        
        order orderObj = new order();
        double input = 110.0;
        
        orderObj.applyDiscount(input);
        
        System.out.println("Expected Discount: 0.0 (unchanged) | Found: " + orderObj.getDiscountPercentage());
        assertEquals("Discount > 100 should be rejected.", 0.0, orderObj.getDiscountPercentage(), 0.001);
        System.out.println("Result: PASS\n");
    }

    @Test
    public void testApplyDiscount_Overwrite() {
        System.out.println("TEST: testApplyDiscount_Overwrite");
        
        order orderObj = new order();
        // First apply 10%
        orderObj.applyDiscount(10.0);
        assertEquals(10.0, orderObj.getDiscountPercentage(), 0.001);
        
        // Overwrite with 20%
        orderObj.applyDiscount(20.0);
        System.out.println("Expected Discount: 20.0 (updated) | Found: " + orderObj.getDiscountPercentage());
        assertEquals("Discount should be updated to new value", 20.0, orderObj.getDiscountPercentage(), 0.001);
        System.out.println("Result: PASS\n");
    }

    @Test
    public void testApplyDiscount_PrecisionBoundary() {
         System.out.println("TEST: testApplyDiscount_PrecisionBoundary");
         order orderObj = new order();
         
         // Just inside upper boundary (99.99%)
         orderObj.applyDiscount(99.99);
         assertEquals(99.99, orderObj.getDiscountPercentage(), 0.001);
         
         // Just inside lower boundary (0.01%)
         orderObj.applyDiscount(0.01);
         assertEquals("0.01% discount should be accepted", 0.01, orderObj.getDiscountPercentage(), 0.001);
         System.out.println("Result: PASS\n");
    }

    // =====================================================
    // ============  Test discount (function) ==============
    // =====================================================

    @Test
    public void testDiscount_NormalValid() {
        System.out.println("TEST: testDiscount_NormalValid");
        
        order orderObj = new order();
        orderObj.addItem(new MenuItem("Steak", "Premium", 100.0));
        
        double discountedPrice = orderObj.discount(20.0);
        
        System.out.println("Original: 100.0 | Discount: 20% | Expected: 80.0 | Found: " + discountedPrice);
        assertEquals(80.0, discountedPrice, 0.001);
        System.out.println("Result: PASS\n");
    }

    @Test
    public void testDiscount_BoundaryZero() {
        System.out.println("TEST: testDiscount_BoundaryZero");
        
        order orderObj = new order();
        orderObj.addItem(new MenuItem("Steak", "Premium", 50.0));
        
        double discountedPrice = orderObj.discount(0.0);
        
        System.out.println("Original: 50.0 | Discount: 0% | Expected: 50.0 | Found: " + discountedPrice);
        assertEquals(50.0, discountedPrice, 0.001);
        System.out.println("Result: PASS\n");
    }

    @Test
    public void testDiscount_BoundaryHundred() {
        System.out.println("TEST: testDiscount_BoundaryHundred");
        
        order orderObj = new order();
        orderObj.addItem(new MenuItem("Steak", "Premium", 50.0));
        
        double discountedPrice = orderObj.discount(100.0);
        
        System.out.println("Original: 50.0 | Discount: 100% | Expected: 0.0 | Found: " + discountedPrice);
        assertEquals(0.0, discountedPrice, 0.001);
        System.out.println("Result: PASS\n");
    }

    @Test
    public void testDiscount_InvalidNegative() {
        System.out.println("TEST: testDiscount_InvalidNegative");
        
        order orderObj = new order();
        orderObj.addItem(new MenuItem("Steak", "Premium", 50.0));
        
        double discountedPrice = orderObj.discount(-10.0);
        
        System.out.println("Original: 50.0 | Discount: -10% | Expected: 50.0 | Found: " + discountedPrice);
        assertEquals(50.0, discountedPrice, 0.001);
        System.out.println("Result: PASS\n");
    }

    @Test
    public void testDiscount_InvalidHigh() {
        System.out.println("TEST: testDiscount_InvalidHigh");
        
        order orderObj = new order();
        orderObj.addItem(new MenuItem("Steak", "Premium", 50.0));
        
        double discountedPrice = orderObj.discount(150.0);
        
        System.out.println("Original: 50.0 | Discount: 150% | Expected: 50.0 | Found: " + discountedPrice);
        assertEquals(50.0, discountedPrice, 0.001);
        System.out.println("Result: PASS\n");
    }
}


