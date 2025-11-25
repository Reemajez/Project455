package project455;

import java.util.List;

public class Payment {

    static final double TAX_RATE = 0.1; // 10% tax rate

    public static double getTaxRate() {
        return TAX_RATE;
    }

    public static double makePayment(order order, double paymentAmount) {
        double subtotal = calculateSubtotal(order);
        double tax = calculateTax(subtotal);
        double total = subtotal + tax;

        // Print receipt details
        System.out.println(String.format("Subtotal (after discount): $%.2f", subtotal));
        System.out.println(String.format("Tax (10%%): $%.2f", tax));
        System.out.println(String.format("Total Due: $%.2f", total));

        if (paymentAmount >= total) {
            double change = paymentAmount - total;
            System.out.println("Payment successful! Change: $" + String.format("%.2f", change));
            return change;
        } else {
            System.out.println("Insufficient payment amount. Payment failed.");
            return Double.NaN; // Return NaN for insufficient payment
        }
    }

    static double calculateSubtotal(order order) {
        double subtotal = 0.0;
        List<MenuItem> orderedItems = order.getOrderedItems();
        
        // 1. Sum up all items
        for (MenuItem item : orderedItems) {
            subtotal += item.getPrice();
        }
        
        // 2. Apply discount if it exists in the order
        double discountPercent = order.getDiscountPercentage();
        if (discountPercent > 0) {
            double discountAmount = subtotal * (discountPercent / 100.0);
            subtotal = subtotal - discountAmount;
        }
        
        return subtotal;
    }

    static double calculateTax(double subtotal) {
        return subtotal * TAX_RATE;
    }
}