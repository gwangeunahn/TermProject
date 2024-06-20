package DeliveryApp;

import java.util.List;

public class FastFood extends Restaurant {
    public FastFood(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "패스트푸드", phoneNumber, menu, prices, location);
    }
}