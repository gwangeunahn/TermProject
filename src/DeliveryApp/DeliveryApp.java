package DeliveryApp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryApp {
    private List<Restaurant> restaurants;

    public DeliveryApp() {
        restaurants = new ArrayList<>();
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void updateRestaurant(int index, Restaurant restaurant) {
        if (index >= 0 && index < restaurants.size()) {
            restaurants.set(index, restaurant);
        }
    }

    public void deleteRestaurant(String name) {
        int i=0;
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equalsIgnoreCase(name)) {
                restaurants.remove(i);
            }
            i++;
        }
    }

    public List<Restaurant> listByCategory(String category) {
        List<Restaurant> categorizedList = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getCategory().equalsIgnoreCase(category)) {
                categorizedList.add(restaurant);
            }
        }
        return categorizedList;
    }

    public List<Restaurant> getRankedRestaurants() {
        restaurants.sort((r1, r2) -> Double.compare(r2.getRating(), r1.getRating()));
        return restaurants;
    }

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Restaurant restaurant : restaurants) {
                writer.write(restaurant.getName() + "," + restaurant.getRating() + "," + restaurant.getCategory() + "," + restaurant.getPhoneNumber() + "," +
                        String.join(";", restaurant.getMenu()) + "," + String.join(";", restaurant.getPrices().stream().map(String::valueOf).toArray(String[]::new)) + "," +
                        restaurant.getLocation());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                double rating = Double.parseDouble(parts[1]);
                String category = parts[2];
                String phoneNumber = parts[3];
                List<String> menu = List.of(parts[4].split(";"));
                List<Double> prices = List.of(parts[5].split(";")).stream().map(Double::parseDouble).toList();
                String location = parts[6];

                Restaurant restaurant;
                switch (category) {
                    case "아시안":
                        restaurant = new AsianFood(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "치킨":
                        restaurant = new Chicken(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "중국집":
                        restaurant = new ChineseFood(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "디저트":
                        restaurant = new Dessert(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "패스트푸드":
                        restaurant = new FastFood(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "일식":
                        restaurant = new JapaneseFood(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "한식":
                        restaurant = new KoreanFood(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "야식":
                        restaurant = new LateNightFood(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "도시락":
                        restaurant = new LunchBox(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "피자":
                        restaurant = new Pizza(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "해산물":
                        restaurant = new Seafood(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "탕":
                        restaurant = new Soup(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "찜":
                        restaurant = new Stew(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "분식":
                        restaurant = new StreetFood(name, rating, phoneNumber, menu, prices, location);
                        break;
                    case "양식":
                        restaurant = new WesternFood(name, rating, phoneNumber, menu, prices, location);
                        break;              
                    default:
                        restaurant = new Restaurant(name, rating, category, phoneNumber, menu, prices, location);
                        break;
                }
                restaurants.add(restaurant);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
