import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location; // ADD THIS IMPORT
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // 1. Our Data
        Map<String, Double> prices = new HashMap<>();
        prices.put("Shoprite", 35.00);
        prices.put("Pick n Pay", 30.00);
        prices.put("Choppies", 32.00);
        prices.put("Zambeef", 50.00);

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("public", Location.EXTERNAL);
        }).start(7000);

        System.out.println("Server started on http://localhost:7000");

        app.get("/api/compare/cheapest", ctx -> {
            try {
                ComparisonStrategy strategy = new PriceComparison(prices);
                ctx.result(strategy.getComparisonResult());
            } catch (NoPricesAvailableException e) {
                ctx.status(404).result("Error: " + e.getMessage());
            }
        });

        app.get("/api/compare/average", ctx -> {
            try {
                ComparisonStrategy strategy = new AveragePriceComparison(prices);
                ctx.result(strategy.getComparisonResult());
            } catch (NoPricesAvailableException e) {
                ctx.status(404).result("Error: " + e.getMessage());
            }
        });
    }
}
