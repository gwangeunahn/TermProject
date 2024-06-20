package DeliveryApp;

import java.util.List;

public class AsianFood extends Restaurant {
    public AsianFood(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "아시안", phoneNumber, menu, prices, location);
    }
}
