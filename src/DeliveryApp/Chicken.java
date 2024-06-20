package DeliveryApp;

import java.util.List;

public class Chicken extends Restaurant {
    public Chicken(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "치킨", phoneNumber, menu, prices, location);
    }
}
