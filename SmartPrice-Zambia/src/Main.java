
package src;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Map<String, Double> prices = new HashMap<>();

        prices.put("Shoprite", 35.00);
        prices.put("Pick n Pay", 30.00);
        prices.put("Choppies", 32.00);

        System.out.println("--- Cheapest Price Comparison ---");

        ComparisonStrategy comparison =
                new PriceComparison(prices);
        comparison.comparePrices();

        System.out.println("\n--- Average Price Comparison ---");

        comparison = new AveragePriceComparison(prices);
        comparison.comparePrices();
    }
}