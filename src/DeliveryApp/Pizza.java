package DeliveryApp;

import java.util.List;

public class Pizza extends Restaurant {
    public Pizza(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "피자", phoneNumber, menu, prices, location);
    }
}