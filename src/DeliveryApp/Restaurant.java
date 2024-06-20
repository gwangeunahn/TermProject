package DeliveryApp;

import java.util.List;

public class Restaurant {
    private String name;
    private double rating;
    private String category;
    private String phoneNumber;
    private List<String> menu;
    private List<Double> prices;
    private String location;

    public Restaurant(String name, double rating, String category, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        this.name = name;
        this.rating = rating;
        this.category = category;
        this.phoneNumber = phoneNumber;
        this.menu = menu;
        this.prices = prices;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getCategory() {
        return category;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<String> getMenu() {
        return menu;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return  "name='" + name + '\'' + "\n" + 
                ", rating=" + rating + "\n" +
                ", category='" + category + '\'' + "\n" +
                ", phoneNumber='" + phoneNumber + '\'' + "\n" +
                ", menu=" + menu + "\n" +
                ", prices=" + prices + "\n" +
                ", location='" + location + '\'' + "\n";
    }
}
