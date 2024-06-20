package DeliveryApp;

import java.util.List;

public class StreetFood extends Restaurant {
    public StreetFood(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "분식", phoneNumber, menu, prices, location);
    }
}