/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project455;

/**
 *
 * @author reemajez
 */

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Component Testing for Restaurant System
 * الهدف: اختبار تفاعل الكلاسات مع بعضها (Integration/Component Level)
 */
public class RestaurantComponentTest {

    // =================================================================
    // Scenario 1: The Full Dining Cycle (Menu -> Order -> Discount -> Payment)
    // =================================================================
    @Test
    public void testFullOrderToPaymentCycle() {
        System.out.println("Component Test: Full Order -> Payment Cycle");

        // 1. Setup Components
        Menu menu = new Menu();
        order customerOrder = new order();

        // 2. Interaction: User finds items in Menu and adds to Order
        // - Item 1: Caesar_Salad ($12.00)
        MenuItem item1 = menu.findMenuItemsByName("Caesar_Salad");
        assertNotNull("Menu should provide item1", item1);
        customerOrder.addItem(item1);

        // - Item 2: Grilled_Salmon ($12.00)
        MenuItem item2 = menu.findMenuItemsByName("Grilled_Salmon");
        assertNotNull("Menu should provide item2", item2);
        customerOrder.addItem(item2);

        // 3. Verify Order State before Payment
        assertEquals("Order should have 2 items", 2, customerOrder.getOrderedItems().size());
        
        // 4. Interaction: Apply Discount (e.g., 50% coupon)
        // Subtotal before discount: 12 + 12 = 24.00
        customerOrder.applyDiscount(50.0); 

        // 5. Interaction: Process Payment via Payment Class
        // Expected Math:
        // - Subtotal after 50% discount: 12.00
        // - Tax (10% of 12.00): 1.20
        // - Total Due: 13.20
        // - Payment Amount: 20.00
        // - Expected Change: 20.00 - 13.20 = 6.80
        
        double paymentAmount = 20.00;
        double actualChange = Payment.makePayment(customerOrder, paymentAmount);

        // Assert the final outcome of the interaction between Menu, Order, and Payment
        assertEquals("Change calculation should consider Menu prices, Order discount, and Tax", 
                6.80, actualChange, 0.01);
        
        System.out.println("Cycle Passed Successfully!\n");
    }

    // =================================================================
    // Scenario 2: Reservation System Interaction (User Input -> System -> Data Update)
    // =================================================================
    @Test
    public void testReservationFlow() {
        System.out.println("Component Test: Reservation System Flow");

        // 1. Setup: Create Data (Tables) and System
        TableReservationSystem resSystem = new TableReservationSystem();
        TableReservation table1 = new TableReservation(1, "01/01/2025", 4, "18:00");
        TableReservation[] tables = { table1 };

        // Verify initial state
        assertTrue("Table should be available initially", table1.isAvailable());

        // 2. Simulate User Input Flow
        // The makeTableReservation method asks for: 
        // Table Num -> Date -> People -> Time
        String simulatedUserInput = "1\n05/20/2025\n4\n19:00\n";
        
        // Save original System.in
        InputStream originalIn = System.in;
        try {
            // Inject simulated input
            System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

            // 3. Execute the System Logic
            resSystem.makeTableReservation(tables);

            // 4. Assert Side Effects (Did the System update the Table object correctly?)
            assertFalse("Table 1 should now be reserved (unavailable)", table1.isAvailable());
            
        } finally {
            // Restore System.in to avoid breaking other tests
            System.setIn(originalIn);
        }
        
        System.out.println("Reservation Flow Passed Successfully!\n");
    }
    
    // =================================================================
    // Scenario 3: Failure Case - Ordering a Non-Existent Item
    // Interaction: Menu -> Order
    // =================================================================
    @Test
    public void testOrderNonExistentItem() {
        System.out.println("Component Test: Ordering Non-Existent Item");

        Menu menu = new Menu();
        order customerOrder = new order();

        // محاولة طلب عنصر غير موجود في القائمة
        // Interaction: Order asks Menu for "Unicorn_Steak", Menu returns null
        MenuItem item = menu.findMenuItemsByName("Unicorn_Steak");
        
        // التحقق من أن القائمة أعادت null
        assertNull("Item should not exist", item);

        // إذا كان العنصر null، يجب ألا يضاف للطلب (نحاكي منطق الـ Controller هنا)
        if (item != null) {
            customerOrder.addItem(item);
        }

        // التحقق: يجب أن تظل قائمة الطلبات فارغة
        assertEquals("Order should remain empty", 0, customerOrder.getOrderedItems().size());
        
        System.out.println("Non-Existent Item Test Passed!\n");
    }

    // =================================================================
    // Scenario 4: Failure Case - Insufficient Payment for an Order
    // Interaction: Order -> Payment
    // =================================================================
    @Test
    public void testInsufficientPaymentFlow() {
        System.out.println("Component Test: Insufficient Payment Flow");

        // 1. Setup Order
        Menu menu = new Menu();
        order customerOrder = new order();
        
        // طلب شيء غالي (Steak مثلاً لو افترضنا وجوده أو عنصرين)
        customerOrder.addItem(menu.findMenuItemsByName("Grilled_Salmon")); // 12.00
        customerOrder.addItem(menu.findMenuItemsByName("Mozzarella_Sticks")); // 12.00
        // Total = 24.00 + Tax

        // 2. Try to pay less than required
        double totalDueApprox = 26.4; // 24 + 2.4 tax
        double paymentAmount = 10.0; // مبلغ غير كافٍ

        // 3. Execute Payment interaction
        double result = Payment.makePayment(customerOrder, paymentAmount);

        // 4. Assert Interaction Result
        // نتوقع أن يرفض كلاس Payment العملية ويعيد NaN
        assertTrue("Payment should fail and return NaN", Double.isNaN(result));
        
        System.out.println("Insufficient Payment Test Passed!\n");
    }

    // =================================================================
    // Scenario 5: Failure Case - Double Booking (Trying to reserve an already reserved table)
    // Interaction: System -> Table Data
    // =================================================================
    @Test
    public void testDoubleReservationFlow() {
        System.out.println("Component Test: Double Booking Flow");

        TableReservationSystem resSystem = new TableReservationSystem();
        TableReservation table1 = new TableReservation(1, "01/01/2025", 4, "18:00");
        TableReservation[] tables = { table1 };

        // 1. الحجز الأول (الناجح)
        String firstUser = "1\n01/01/2025\n4\n18:00\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(firstUser.getBytes()));
        resSystem.makeTableReservation(tables);
        
        // تأكيد أن الطاولة أصبحت محجوزة
        assertFalse("Table should be reserved now", table1.isAvailable());

        // 2. الحجز الثاني (الفاشل) لنفس الطاولة
        // نحاول حجز نفس الطاولة مرة أخرى
        String secondUser = "1\n01/01/2025\n4\n18:00\n";
        System.setIn(new ByteArrayInputStream(secondUser.getBytes()));
        
        resSystem.makeTableReservation(tables); // يجب أن يطبع رسالة خطأ ولا يغير شيئاً

        // 3. التحقق: الحالة يجب أن تظل كما هي (محجوزة) ولا يحدث خطأ في النظام
        assertFalse("Table should remain reserved", table1.isAvailable());
        
        // استعادة الـ Input الطبيعي
        System.setIn(originalIn);
        
        System.out.println("Double Booking Test Passed!\n");
    }
}