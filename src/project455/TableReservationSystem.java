package project455;

import java.util.Scanner;

public class TableReservationSystem {
    
    // UPDATED: Now accepts 'Scanner input' as a parameter
    public void makeTableReservation(Scanner input, TableReservation[] reservations) {
        // REMOVED: Scanner input = new Scanner(System.in); 
        // We use the scanner passed from Main instead of creating a new one.
        
        System.out.print("Enter the table number: ");
        int table_Number = input.nextInt();
        System.out.print("Enter the date (MM/DD/YYYY): ");
        String date_ = input.next();
        System.out.print("Enter the number of people: ");
        int number_Of_People = input.nextInt();
        System.out.print("Enter the reservation time: ");
        String reservation_Time = input.next();
        
        TableReservation reservation = new TableReservation(table_Number,date_,number_Of_People,reservation_Time);
        for (TableReservation res : reservations) {
            if (res.getTableNumber() == table_Number) {
                reservation = res;
                break;
            }
        }
    
        if (reservation.isAvailable()) {
            reservation.reserveTable();
            System.out.println("Table " + table_Number + " reserved for " + number_Of_People + " people at " + reservation_Time);
            System.out.println("Table reserved!");
        } else {
            System.out.println("Table " + table_Number + " is not available for the given date and time.");
        }
    }
}