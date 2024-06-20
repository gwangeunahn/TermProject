package DeliveryApp;

import java.util.List;

public class Seafood extends Restaurant {
    public Seafood(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "해산물", phoneNumber, menu, prices, location);
    }
}