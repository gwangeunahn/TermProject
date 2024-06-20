package DeliveryApp;

import java.util.List;

public class WesternFood extends Restaurant {
    public WesternFood(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "양식", phoneNumber, menu, prices, location);
    }
}