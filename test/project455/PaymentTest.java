
package project455;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)


public class PaymentTest {
    
    public PaymentTest() {
    }
    
    @Test
    public void test01_GetTaxRate() {
        System.out.println("Testing 1 getTaxRate...");
       
        double expectedResult = 0.1; 
        double actualResult = Payment.getTaxRate();
        
        assertEquals(expectedResult, actualResult, 0.001);
    }

    
    @Test
    public void test02_MakePayment() {
        System.out.println("Testing 2 makePayment...");
        
        // 1.(Order)
        order myOrder = new order();
        // سعر العنصر 10.0
        myOrder.addItem(new MenuItem("Burger", "Delicious", 10.0)); 
        
        // الحسابات المتوقعة:
        // Subtotal = 10.0
        // Tax (10%) = 1.0
        // Total Due = 11.0
        
        // 2. مبلغ الدفع (15.0)
        double paymentAmount = 15.0;
        
        // 3. الباقي المتوقع (15.0 - 11.0 = 4.0)
        double expectedChange = 4.0;
        
        // تنفيذ الدالة
        double actualChange = Payment.makePayment(myOrder, paymentAmount);
        
        assertEquals(expectedChange, actualChange, 0.01);
    }

    @Test
    public void test03_CalculateSubtotal() {
        System.out.println("Testing 3 calculateSubtotal...");
        
        // تجهيز طلب بعنصرين
        order myOrder = new order();
        myOrder.addItem(new MenuItem("Item1", "Desc", 10.0));
        myOrder.addItem(new MenuItem("Item2", "Desc", 20.0));
        
        // المجموع المتوقع: 10 + 20 = 30.0
        double expectedResult = 30.0;
        
       
        double actualResult = Payment.calculateSubtotal(myOrder);
        
        assertEquals(expectedResult, actualResult, 0.01);
    }
    
    @Test
    public void test04_CalculateTax() {
        System.out.println("Testing 4 calculateTax...");
        
        double subtotal = 100.0;
        
        double expectedResult = 10.0;
        
        double actualResult = Payment.calculateTax(subtotal);
        
        assertEquals(expectedResult, actualResult, 0.01);
    }
    
    @Test
    public void test05_MakePaymentInsufficient() {
        System.out.println("Testing 5 makePayment with insufficient funds...");
        
        order myOrder = new order();
        myOrder.addItem(new MenuItem("Burger", "Desc", 10.0)); 
        // Total should be 11.0 (10 + 1 tax)
        
        double paymentAmount = 5.0; 
        
        double result = Payment.makePayment(myOrder, paymentAmount);
        
        // التحقق من أن النتيجة هي NaN (Not a Number)
        assertTrue("Should return NaN for insufficient payment", Double.isNaN(result));
    }

 
    @Test
    public void test06_CalculateSubtotalWithDiscount() {
        System.out.println("Testing 6 calculateSubtotal with Discount...");
        
        order myOrder = new order();
        myOrder.addItem(new MenuItem("Steak", "Desc", 100.0));
        
        myOrder.applyDiscount(20.0);
        
        double expectedResult = 80.0;
        double actualResult = Payment.calculateSubtotal(myOrder);
        
        assertEquals(expectedResult, actualResult, 0.01);
    }

   
    @Test
    public void test07_CalculateSubtotalEmptyOrder() {
        System.out.println("Testing 7 calculateSubtotal with empty order...");
        
        order myOrder = new order();
        // لم نضف أي عنصر
        
        double expectedResult = 0.0;
        double actualResult = Payment.calculateSubtotal(myOrder);
        
        assertEquals(expectedResult, actualResult, 0.01);
    }
    
}