package sg.edu.rp.c346.id20041162.sa_assignment;

import java.util.Comparator;

public class Product {
    private String name;
    private String date;
    private int year;
    private int month;
    private int day;

    public Product(String name, String date, int year, int month, int day) {
        this.name = name;
        this.date = date;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public static Comparator<Product> ProductNameComparator = new Comparator<Product>() {

        public int compare(Product p1, Product p2) {
            String ProductName1 = p1.getName().toUpperCase();
            String ProductName2 = p2.getName().toUpperCase();

            //ascending order
            return ProductName1.compareTo(ProductName2);

        }};
}
