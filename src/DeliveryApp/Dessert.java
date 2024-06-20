package DeliveryApp;

import java.util.List;

public class Dessert extends Restaurant {
    public Dessert(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "디저트", phoneNumber, menu, prices, location);
    }
}