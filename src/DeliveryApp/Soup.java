package DeliveryApp;

import java.util.List;

public class Soup extends Restaurant {
    public Soup(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "탕", phoneNumber, menu, prices, location);
    }
}