package DeliveryApp;

import java.util.List;

public class JapaneseFood extends Restaurant {
    public JapaneseFood(String name, double rating, String phoneNumber, List<String> menu, List<Double> prices, String location) {
        super(name, rating, "일식", phoneNumber, menu, prices, location);
    }
}
