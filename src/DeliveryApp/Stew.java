package DeliveryApp;

import java.util.List;

public class Stew extends Restaurant {
    public Stew(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "찜", phoneNumber, menu, prices, location);
    }
}