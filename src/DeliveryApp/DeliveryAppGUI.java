package DeliveryApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryAppGUI implements DCRUD {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private DeliveryApp deliveryApp;
    private DefaultListModel<String> restaurantListModel;
    private JList<String> restaurantList;

    public DeliveryAppGUI() {
        frame = new JFrame("배달 앱");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        deliveryApp = new DeliveryApp();
        restaurantListModel = new DefaultListModel<>();
        restaurantList = new JList<>(restaurantListModel);

        deliveryApp.loadFromFile("restaurant.txt");

        initHomeScreen();
        initCategoryScreen();
        initSearchScreen();
        initSettingScreen();
        initRestaurantListScreen();

        frame.add(mainPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> deliveryApp.saveToFile("restaurant.txt")));
    }

    private void initHomeScreen() {
        JPanel homePanel = new JPanel(new GridLayout(3, 1));
        JButton categoryButton = new JButton("카테고리");
        JButton searchButton = new JButton("검색");
        JButton settingButton = new JButton("설정");

        categoryButton.addActionListener(e -> cardLayout.show(mainPanel, "CategoryScreen"));
        searchButton.addActionListener(e -> cardLayout.show(mainPanel, "SearchScreen"));
        settingButton.addActionListener(e -> cardLayout.show(mainPanel, "SettingScreen"));

        homePanel.add(categoryButton);
        homePanel.add(searchButton);
        homePanel.add(settingButton);
        mainPanel.add(homePanel, "HomeScreen");
    }

    private void initCategoryScreen() {
        JPanel categoryPanel = new JPanel(new BorderLayout());
        JList<String> categoryList = new JList<>(new String[]{
            "아시안", "치킨", "중국집", "디저트", "패스트푸드", 
            "일식", "한식", "야식", "도시락", 
            "피자", "해산물", "탕", "찜", "분식", "양식"
        });

        categoryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String category = categoryList.getSelectedValue();
                showRestaurantsByCategory(category);
            }
        });

        categoryPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);
        categoryPanel.add(createHomeButtonPanel(), BorderLayout.SOUTH);
        mainPanel.add(categoryPanel, "CategoryScreen");
    }

    private void initRestaurantListScreen() {
        JPanel restaurantListPanel = new JPanel(new BorderLayout());
        restaurantList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String restaurantName = restaurantList.getSelectedValue();
                showRestaurantDetails(restaurantName);
            }
        });

        restaurantListPanel.add(new JScrollPane(restaurantList), BorderLayout.CENTER);
        restaurantListPanel.add(createHomeButtonPanel(), BorderLayout.SOUTH);
        mainPanel.add(restaurantListPanel, "RestaurantListScreen");
    }

    private void showRestaurantsByCategory(String category) {
        restaurantListModel.clear();
        List<Restaurant> restaurants = deliveryApp.listByCategory(category);
        for (Restaurant restaurant : restaurants) {
            restaurantListModel.addElement(restaurant.getName());
        }
        cardLayout.show(mainPanel, "RestaurantListScreen");
    }

    private void showRestaurantDetails(String restaurantName) {
        for (Restaurant restaurant : deliveryApp.getRestaurants()) {
            if (restaurant.getName().equals(restaurantName)) {
                JOptionPane.showMessageDialog(frame, restaurant.toString(), "음식점 정보", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }

    private void initSearchScreen() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        searchField.addActionListener(e -> searchRestaurant(searchField.getText()));
        searchPanel.add(searchField, BorderLayout.NORTH);
        searchPanel.add(createHomeButtonPanel(), BorderLayout.SOUTH);
        mainPanel.add(searchPanel, "SearchScreen");
    }

    public void searchRestaurant(String keyword) {
        restaurantListModel.clear();
        for (Restaurant restaurant : deliveryApp.getRestaurants()) {
            if (restaurant.getName().contains(keyword)) {
                restaurantListModel.addElement(restaurant.getName());
            }
        }
        cardLayout.show(mainPanel, "RestaurantListScreen");
    }

    private void initSettingScreen() {
        JPanel settingPanel = new JPanel(new GridLayout(4, 1));
        JButton addButton = new JButton("추가");
        JButton deleteButton = new JButton("삭제");
        JButton updateButton = new JButton("수정");
        JButton saveButton = new JButton("저장");

        addButton.addActionListener(e -> addRestaurant());
        deleteButton.addActionListener(e -> deleteRestaurant());
        updateButton.addActionListener(e -> updateRestaurant());
        saveButton.addActionListener(e -> deliveryApp.saveToFile("restaurant.txt"));

        settingPanel.add(addButton);
        settingPanel.add(deleteButton);
        settingPanel.add(updateButton);
        settingPanel.add(saveButton);
        settingPanel.add(createHomeButtonPanel());
        mainPanel.add(settingPanel, "SettingScreen");
    }

    private JPanel createHomeButtonPanel() {
        JPanel homeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton homeButton = new JButton("홈");
        homeButton.addActionListener(e -> cardLayout.show(mainPanel, "HomeScreen"));
        homeButtonPanel.add(homeButton);
        return homeButtonPanel;
    }

    public void addRestaurant() {
        JTextField categoryField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ratingField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField menuField = new JTextField();
        JTextField pricesField = new JTextField();
        JTextField locationField = new JTextField();

        Object[] message = {
            "카테고리:", categoryField,
            "이름:", nameField,
            "평점:", ratingField,
            "전화번호:", phoneField,
            "메뉴 (세미콜론으로 구분):", menuField,
            "가격 (세미콜론으로 구분):", pricesField,
            "위치:", locationField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "음식점 추가", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String category = categoryField.getText();
            String name = nameField.getText();
            double rating = Double.parseDouble(ratingField.getText());
            String phone = phoneField.getText();
            List<String> menu = Arrays.asList(menuField.getText().split(";"));
            List<Double> prices = Arrays.asList(pricesField.getText().split(";")).stream().map(Double::parseDouble).toList();
            String location = locationField.getText();
            Restaurant restaurant;
            switch (category) {
                case "아시안":
                    restaurant = new AsianFood(name, rating, phone, menu, prices, location);
                    break;
                case "치킨":
                    restaurant = new Chicken(name, rating, phone, menu, prices, location);
                    break;
                case "중국집":
                    restaurant = new ChineseFood(name, rating, phone, menu, prices, location);
                    break;
                case "디저트":
                    restaurant = new Dessert(name, rating, phone, menu, prices, location);
                    break;
                case "패스트푸드":
                    restaurant = new FastFood(name, rating, phone, menu, prices, location);
                    break;
                case "일식":
                    restaurant = new JapaneseFood(name, rating, phone, menu, prices, location);
                    break;
                case "한식":
                    restaurant = new KoreanFood(name, rating, phone, menu, prices, location);
                    break;
                case "야식":
                    restaurant = new LateNightFood(name, rating, phone, menu, prices, location);
                    break;
                case "도시락":
                    restaurant = new LunchBox(name, rating, phone, menu, prices, location);
                    break;
                case "피자":
                    restaurant = new Pizza(name, rating, phone, menu, prices, location);
                    break;
                case "해산물":
                    restaurant = new Seafood(name, rating, phone, menu, prices, location);
                    break;
                case "탕":
                    restaurant = new Soup(name, rating, phone, menu, prices, location);
                    break;
                case "찜":
                    restaurant = new Stew(name, rating, phone, menu, prices, location);
                    break;
                case "분식":
                    restaurant = new StreetFood(name, rating, phone, menu, prices, location);
                    break;
                case "양식":
                    restaurant = new WesternFood(name, rating, phone, menu, prices, location);
                    break;
                default:
                    restaurant = new Restaurant(name, rating, category, phone, menu, prices, location);
            }
            deliveryApp.addRestaurant(restaurant);
        }
    }

    public void deleteRestaurant() {
        JTextField searchField = new JTextField();
        Object[] message = { "삭제할 음식점 이름:", searchField };
        int option = JOptionPane.showConfirmDialog(frame, message, "음식점 삭제", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = searchField.getText();
            deliveryApp.deleteRestaurant(name);
        }
    }

    public void updateRestaurant() {
        JTextField searchField = new JTextField();
        Object[] searchMessage = { "수정할 음식점 이름:", searchField };
        int searchOption = JOptionPane.showConfirmDialog(frame, searchMessage, "음식점 수정", JOptionPane.OK_CANCEL_OPTION);
        if (searchOption == JOptionPane.OK_OPTION) {
            String name = searchField.getText();
            int index = -1;
            for (int i = 0; i < deliveryApp.getRestaurants().size(); i++) {
                if (deliveryApp.getRestaurants().get(i).getName().equalsIgnoreCase(name)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                Restaurant restaurant = deliveryApp.getRestaurants().get(index);
                JTextField categoryField = new JTextField(restaurant.getCategory());
                JTextField nameField = new JTextField(restaurant.getName());
                JTextField ratingField = new JTextField(String.valueOf(restaurant.getRating()));
                JTextField phoneField = new JTextField(restaurant.getPhoneNumber());
                JTextField menuField = new JTextField(String.join(";", restaurant.getMenu()));
                JTextField pricesField = new JTextField(restaurant.getPrices().stream().map(String::valueOf).collect(Collectors.joining(";")));
                JTextField locationField = new JTextField(restaurant.getLocation());

                Object[] message = {
                    "카테고리:", categoryField,
                    "이름:", nameField,
                    "평점:", ratingField,
                    "전화번호:", phoneField,
                    "메뉴 (세미콜론으로 구분):", menuField,
                    "가격 (세미콜론으로 구분):", pricesField,
                    "위치:", locationField
                };

                int option = JOptionPane.showConfirmDialog(frame, message, "음식점 수정", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String category = categoryField.getText();
                    String newName = nameField.getText();
                    double rating = Double.parseDouble(ratingField.getText());
                    String phone = phoneField.getText();
                    List<String> menu = Arrays.asList(menuField.getText().split(";"));
                    List<Double> prices = Arrays.asList(pricesField.getText().split(";")).stream().map(Double::parseDouble).toList();
                    String location = locationField.getText();
                    Restaurant updatedRestaurant;
                    switch (category) {
                        case "아시안":
                            updatedRestaurant = new AsianFood(newName, rating, phone, menu, prices, location);
                            break;
                        case "치킨":
                            updatedRestaurant = new Chicken(name, rating, phone, menu, prices, location);
                            break;
                        case "중국집":
                            updatedRestaurant = new ChineseFood(name, rating, phone, menu, prices, location);
                            break;
                        case "디저트":
                            updatedRestaurant = new Dessert(name, rating, phone, menu, prices, location);
                            break;
                        case "패스트푸드":
                            updatedRestaurant = new FastFood(name, rating, phone, menu, prices, location);
                            break;
                        case "일식":
                            updatedRestaurant = new JapaneseFood(name, rating, phone, menu, prices, location);
                            break;
                        case "한식":
                            updatedRestaurant = new KoreanFood(name, rating, phone, menu, prices, location);
                            break;
                        case "야식":
                            updatedRestaurant = new LateNightFood(name, rating, phone, menu, prices, location);
                            break;
                        case "도시락":
                            updatedRestaurant = new LunchBox(name, rating, phone, menu, prices, location);
                            break;
                        case "피자":
                            updatedRestaurant = new Pizza(name, rating, phone, menu, prices, location);
                            break;
                        case "해산물":
                            updatedRestaurant = new Seafood(name, rating, phone, menu, prices, location);
                            break;
                        case "탕":
                            updatedRestaurant = new Soup(name, rating, phone, menu, prices, location);
                            break;
                        case "찜":
                            updatedRestaurant = new Stew(name, rating, phone, menu, prices, location);
                            break;
                        case "분식":
                            updatedRestaurant = new StreetFood(name, rating, phone, menu, prices, location);
                            break;
                        case "양식":
                            updatedRestaurant = new WesternFood(name, rating, phone, menu, prices, location);
                            break;
                        default:
                            updatedRestaurant = new Restaurant(newName, rating, category, phone, menu, prices, location);
                    }
                    deliveryApp.updateRestaurant(index, updatedRestaurant);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "음식점을 찾을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DeliveryAppGUI::new);
    }
}
