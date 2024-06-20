package DeliveryApp;

import java.util.List;

public class KoreanFood extends Restaurant {
    public KoreanFood(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "한식", phoneNumber, menu, prices, location);
    }
}