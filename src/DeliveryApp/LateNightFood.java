package DeliveryApp;

import java.util.List;

public class LateNightFood extends Restaurant {
    public LateNightFood(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "야식", phoneNumber, menu, prices, location);
    }
}