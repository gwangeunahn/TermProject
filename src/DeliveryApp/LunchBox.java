package DeliveryApp;

import java.util.List;

public class LunchBox extends Restaurant {
    public LunchBox(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "도시락", phoneNumber, menu, prices, location);
    }
}