package DeliveryApp;

import java.util.List;

public class ChineseFood extends Restaurant {
    public ChineseFood(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "중국집", phoneNumber, menu, prices, location);
    }
}