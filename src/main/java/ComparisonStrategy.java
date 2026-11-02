public interface ComparisonStrategy {
    // Changed from void to String, and added throws
    String getComparisonResult() throws NoPricesAvailableException;
}