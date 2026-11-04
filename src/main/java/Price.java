
SmartPrice-Zambia/
├── src/
│   └── main/
│       └── java/
│           ├── Main.java
│           ├── User.java
│           ├── Customer.java
│           ├── ShopOwner.java
│           ├── Shop.java
│           ├── Product.java
│           ├── Price.java
│           ├── PriceComparisonService.java
│           ├── ComparisonStrategy.java
│           ├── PriceComparison.java
│           ├── AveragePriceComparison.java
│           └── NoPricesAvailableException.java
├── public/
│   ├── index.html
│   ├── style.css
│   └── script.js
└── pom.xml

  
public class Price {
    private String productName;
    private String shopName;
    private double amount;

    public Price(String productName, String shopName, double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        this.productName = productName;
        this.shopName = shopName;
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public String getShopName() {
        return shopName;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return productName + " at " + shopName + ": K" + amount;
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceComparisonService {

    private List<Shop> shops;

    public PriceComparisonService(List<Shop> shops) {
        this.shops = shops;
    }

    // Get all prices for a product
    public List<Price> getPrices(String productName)
            throws NoPricesAvailableException {

        List<Price> prices = new ArrayList<>();

        for (Shop shop : shops) {
            for (Product product : shop.getInventory()) {

                if (product.getName().equalsIgnoreCase(productName)) {

                    prices.add(new Price(
                            product.getName(),
                            shop.getShopName(),
                            product.getPrice()
                    ));
                }
            }
        }

        if (prices.isEmpty()) {
            throw new NoPricesAvailableException(
                    "No prices available for " + productName
            );
        }

        return prices;
    }

    // Find the cheapest product price
    public Price getCheapestPrice(String productName)
            throws NoPricesAvailableException {

        List<Price> prices = getPrices(productName);

        return Collections.min(prices,
                Comparator.comparingDouble(Price::getAmount));
    }

    // Find the average price
    public double getAveragePrice(String productName)
            throws NoPricesAvailableException {

        List<Price> prices = getPrices(productName);

        double total = 0;

        for (Price price : prices) {
            total += price.getAmount();
        }

        return total / prices.size();
    }

    // Sort prices from cheapest to most expensive
    public List<Price> sortPrices(String productName)
            throws NoPricesAvailableException {

        List<Price> prices = getPrices(productName);

        prices.sort(
                Comparator.comparingDouble(Price::getAmount)
        );

        return prices;
    }

    // Calculate the cheapest possible shopping basket
    // Basket format: Product name -> Quantity
    public double calculateBasketTotal(
            Map<String, Integer> basket)
            throws NoPricesAvailableException {

        double total = 0;

        for (Map.Entry<String, Integer> item : basket.entrySet()) {

            String productName = item.getKey();
            int quantity = item.getValue();

            if (quantity <= 0) {
                throw new IllegalArgumentException(
                        "Quantity must be greater than zero."
                );
            }

            Price cheapest = getCheapestPrice(productName);

            total += cheapest.getAmount() * quantity;
        }

        return total;
    }

    // Find the cheapest shop for every product in the basket
    public Map<String, Price> getCheapestBasketPrices(
            Map<String, Integer> basket)
            throws NoPricesAvailableException {

        Map<String, Price> results = new HashMap<>();

        for (String productName : basket.keySet()) {

            Price cheapest = getCheapestPrice(productName);

            results.put(productName, cheapest);
        }

        return results;
    }
}

import java.util.Map;
import java.util.HashMap;

// Initialize the price comparison service
PriceComparisonService priceService =
        new PriceComparisonService(allShops);

// Endpoint 1: Get all prices for a product
app.get("/api/prices/{product}", ctx -> {
    try {
        String productName = ctx.pathParam("product");

        ctx.json(priceService.getPrices(productName));

    } catch (NoPricesAvailableException e) {
        ctx.status(404).result(e.getMessage());
    }
});

// Endpoint 2: Get the cheapest price
app.get("/api/prices/cheapest/{product}", ctx -> {
    try {
        String productName = ctx.pathParam("product");

        ctx.json(priceService.getCheapestPrice(productName));

    } catch (NoPricesAvailableException e) {
        ctx.status(404).result(e.getMessage());
    }
});

// Endpoint 3: Get the average price
app.get("/api/prices/average/{product}", ctx -> {
    try {
        String productName = ctx.pathParam("product");

        double average =
                priceService.getAveragePrice(productName);

        Map<String, Object> result = new HashMap<>();

        result.put("product", productName);
        result.put("averagePrice", average);

        ctx.json(result);

    } catch (NoPricesAvailableException e) {
        ctx.status(404).result(e.getMessage());
    }
});

// Endpoint 4: Sort prices from cheapest to most expensive
app.get("/api/prices/sorted/{product}", ctx -> {
    try {
        String productName = ctx.pathParam("product");

        ctx.json(priceService.sortPrices(productName));

    } catch (NoPricesAvailableException e) {
        ctx.status(404).result(e.getMessage());
    }
});

app.post("/api/basket/total", ctx -> {
    try {
        BasketRequest request =
                ctx.bodyAsClass(BasketRequest.class);

        double total =
                priceService.calculateBasketTotal(
                        request.getBasket()
                );

        Map<String, Object> result = new HashMap<>();

        result.put("total", total);
        result.put("currency", "ZMW");

        ctx.json(result);

    } catch (NoPricesAvailableException e) {
        ctx.status(404).result(e.getMessage());

    } catch (IllegalArgumentException e) {
        ctx.status(400).result(e.getMessage());
    }
});

// Request class for shopping baskets
public static class BasketRequest {

    private Map<String, Integer> basket;

    public Map<String, Integer> getBasket() {
        return basket;
    }

    public void setBasket(Map<String, Integer> basket) {
        this.basket = basket;
    }
}

{
    "basket": {
        "Mealie Meal 25kg": 2,
        "Cooking Oil 2L": 3
    }
}

