package project455;

import java.util.ArrayList;
import java.util.List;

class MenuItem {

    private String name;
    private String description;
    private double price;

    public MenuItem(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return getName() + " $" + getPrice() + " - " + getDescription();
    }
}

class Menu {

    private List<MenuItem> appetizers;
    private List<MenuItem> mainCourses;
    private List<MenuItem> desserts;

    public Menu() {
        appetizers = new ArrayList<>();
        appetizers.add(new MenuItem("Caesar_Salad", "Fresh romaine lettuce with Caesar dressing", 12.00));
        appetizers.add(new MenuItem("Mozzarella_Sticks", "Fried mozzarella cheese sticks served with marinara sauce", 12.00));

        mainCourses = new ArrayList<>();
        mainCourses.add(new MenuItem("Grilled_Salmon", "Grilled Atlantic salmon fillet with lemon butter sauce", 12.00));
        mainCourses.add(new MenuItem("Chicken_Alfredo", "Grilled chicken breast with creamy Alfredo sauce and fettuccine pasta", 12.00));

        desserts = new ArrayList<>();
        desserts.add(new MenuItem("Chocolate_Cake", "Decadent chocolate cake with chocolate frosting", 6.00));
        desserts.add(new MenuItem("Cheesecake", "Classic New York style cheesecake with strawberry topping", 6.00));
    }

    public List<MenuItem> getAppetizers() {
        return appetizers;
    }

    public List<MenuItem> getMainCourses() {
        return mainCourses;
    }

    public List<MenuItem> getDesserts() {
        return desserts;
    }

    public void addAppetizer(String name, String desc, double price) {
        appetizers.add(new MenuItem(name, desc, price));
    }

    public void addMainCourse(String name, String desc, double price) {
        mainCourses.add(new MenuItem(name, desc, price));
    }

    public void addDessert(String name, String desc, double price) {
        desserts.add(new MenuItem(name, desc, price));
    }
    
    
    public void DisplayMenu() {
        System.out.println("---------- Menu ----------");
        displayMenuByCategory("Appetizers", appetizers);
        displayMenuByCategory("Main Courses", mainCourses);
        displayMenuByCategory("Desserts", desserts);
    }

    private void displayMenuByCategory(String category, List<MenuItem> items) {
        System.out.println(category + ":");
        for (MenuItem item : items) {
            System.out.println("- " + item.getName() + " $" + item.getPrice() + " - " + item.getDescription());
        }
    }

    public MenuItem findMenuItemsByName(String itemName) {
        for (MenuItem item : appetizers) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        for (MenuItem item : mainCourses) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        for (MenuItem item : desserts) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
}
