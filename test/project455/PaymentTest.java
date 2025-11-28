/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package project455;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author reemajez
 */
public class PaymentTest {
    
    public PaymentTest() {
    }
    
    
    //  Test of getTaxRate method, of class Payment.
  
    @Test
    public void testGetTaxRate() {
        System.out.println("Testing getTaxRate...");
       
        // القيمة المتوقعة حسب كلاس Payment هي 0.1
        double expectedResult = 0.1; 
        double actualResult = Payment.getTaxRate();
        
        // الرقم 0.001 هو نسبة السماح بالخطأ في الكسور (Delta)
        assertEquals(expectedResult, actualResult, 0.001);
    }

    
     // Test of makePayment method, of class Payment.
    
    @Test
    public void testMakePayment() {
        System.out.println("Testing makePayment...");
        
        // 1. تجهيز الطلب (Order)
        order myOrder = new order();
        // سعر العنصر 10.0
        myOrder.addItem(new MenuItem("Burger", "Delicious", 10.0)); 
        
        // الحسابات المتوقعة:
        // Subtotal = 10.0
        // Tax (10%) = 1.0
        // Total Due = 11.0
        
        // 2. مبلغ الدفع (ندفع 15.0)
        double paymentAmount = 15.0;
        
        // 3. الباقي المتوقع (15.0 - 11.0 = 4.0)
        double expectedChange = 4.0;
        
        // تنفيذ الدالة
        double actualChange = Payment.makePayment(myOrder, paymentAmount);
        
        assertEquals(expectedChange, actualChange, 0.01);
    }

    
     // Test of calculateSubtotal method, of class Payment.
     // الهدف: التأكد من جمع أسعار العناصر بشكل صحيح
     
    @Test
    public void testCalculateSubtotal() {
        System.out.println("Testing calculateSubtotal...");
        
        // تجهيز طلب بعنصرين
        order myOrder = new order();
        myOrder.addItem(new MenuItem("Item1", "Desc", 10.0));
        myOrder.addItem(new MenuItem("Item2", "Desc", 20.0));
        
        // المجموع المتوقع: 10 + 20 = 30.0
        double expectedResult = 30.0;
        
       
        double actualResult = Payment.calculateSubtotal(myOrder);
        
        assertEquals(expectedResult, actualResult, 0.01);
    }
    
     // Test of calculateTax method, of class Payment.
     // الهدف: التأكد من حساب قيمة الضريبة بناء على المجموع الفرعي
   
    @Test
    public void testCalculateTax() {
        System.out.println("Testing calculateTax...");
        
        double subtotal = 100.0;
        
        // الضريبة 10%، يعني 10.0
        double expectedResult = 10.0;
        
        double actualResult = Payment.calculateTax(subtotal);
        
        assertEquals(expectedResult, actualResult, 0.01);
    }
    
    @Test
    public void testMakePaymentInsufficient() {
        System.out.println("Testing makePayment with insufficient funds...");
        
        order myOrder = new order();
        myOrder.addItem(new MenuItem("Burger", "Desc", 10.0)); 
        // Total should be 11.0 (10 + 1 tax)
        
        double paymentAmount = 5.0; // دفعنا 5 فقط
        
        double result = Payment.makePayment(myOrder, paymentAmount);
        
        // التحقق من أن النتيجة هي NaN (Not a Number)
        assertTrue("Should return NaN for insufficient payment", Double.isNaN(result));
    }

 
    @Test
    public void testCalculateSubtotalWithDiscount() {
        System.out.println("Testing calculateSubtotal with Discount...");
        
        order myOrder = new order();
        myOrder.addItem(new MenuItem("Steak", "Desc", 100.0));
        
        // تطبيق خصم 20%
        // (يجب أن يكون الخصم 20 دولار، والمجموع الجديد 80 دولار)
        myOrder.applyDiscount(20.0);
        
        double expectedResult = 80.0;
        double actualResult = Payment.calculateSubtotal(myOrder);
        
        assertEquals(expectedResult, actualResult, 0.01);
    }

   
    @Test
    public void testCalculateSubtotalEmptyOrder() {
        System.out.println("Testing calculateSubtotal with empty order...");
        
        order myOrder = new order();
        // لم نضف أي عنصر
        
        double expectedResult = 0.0;
        double actualResult = Payment.calculateSubtotal(myOrder);
        
        assertEquals(expectedResult, actualResult, 0.01);
    }
    
}