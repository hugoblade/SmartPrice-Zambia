package src;

import java.util.Map;

public class AveragePriceComparison implements ComparisonStrategy {

private Map<String, Double> shopPrices;

public AveragePriceComparison(Map<String, Double> shopPrices) {
    this.shopPrices = shopPrices;
}

@Override
public void comparePrices() {
    try {
        if (shopPrices == null || shopPrices.isEmpty()) {
            throw new NoPricesAvailableException(
                "No prices available to calculate the average."
            );
        }

        double total = 0;
        int count = 0;

        for (Double price : shopPrices.values()) {
            if (price != null && price >= 0) {
                total += price;
                count++;
            }
        }

        if (count == 0) {
            throw new NoPricesAvailableException(
                "No valid prices available to calculate the average."
            );
        }

        double average = total / count;

       System.out.printf("Average price: K%.2f%n", average);

    } catch (NoPricesAvailableException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

}
