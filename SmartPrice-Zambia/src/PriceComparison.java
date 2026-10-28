package src;

import java.util.Map;

public class PriceComparison implements ComparisonStrategy {

private Map<String, Double> shopPrices;

public PriceComparison(Map<String, Double> shopPrices) {
    this.shopPrices = shopPrices;
}

@Override
public void comparePrices() {
    try {
        if (shopPrices == null || shopPrices.isEmpty()) {
            throw new NoPricesAvailableException(
                "No prices available for comparison."
            );
        }

        String cheapestShop = null;
        double lowestPrice = Double.MAX_VALUE;

        for (Map.Entry<String, Double> entry : shopPrices.entrySet()) {
            Double price = entry.getValue();

            if (price != null && price >= 0 && price < lowestPrice) {
                lowestPrice = price;
                cheapestShop = entry.getKey();
            }
        }

        if (cheapestShop == null) {
            throw new NoPricesAvailableException(
                "No valid prices available for comparison."
            );
        }

        System.out.println("Cheapest shop: " + cheapestShop);
        System.out.println("Lowest price: K" + lowestPrice);

    } catch (NoPricesAvailableException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

}

