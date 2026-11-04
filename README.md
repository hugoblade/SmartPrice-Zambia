# SmartPrice Zambia

## 1. The Problem
Consumers in Zambia often spend significant time and money travelling to different shops to compare prices for basic groceries and household products. Our system solves this by allowing users to compare grocery prices across different registered shops.

## 2. How to Run the Project
1. Ensure you have Java 21 installed.
2. Open the project in VS Code or IntelliJ.
3. Run the `Main.java` file. 
4. Open your browser and go to `http://localhost:7000` to view the frontend.
5. API endpoints are available at `http://localhost:7000/api/compare/cheapest` and `/api/compare/average`.

## 3. Main Classes (OOP Design)
*   `User`, `Customer`, `ShopOwner`: Demonstrates **Inheritance** and **Encapsulation**.
*   `Shop`, `Product`: Demonstrates **Collections** and **Composition**.
*   `ComparisonStrategy`: An **Interface** used to implement the Strategy Pattern.
*   `PriceComparison`, `AveragePriceComparison`: Demonstrates **Polymorphism** (the same method behaves differently for different objects).
*   `NoPricesAvailableException`: A **Custom Exception** used for error handling.
*   `Main`: The Javalin server setup and REST API endpoints.

## 4. Team Members
| Full Name | Student Number |
|-----------|----------------|
| Thokozile Zulu | 2200791 |
| Limpo Mwenya Musonda | 2420990 |
| Chandi Chileshe | 2410226 |
| Gaston Mylu Mulundu | 2410364 |
| Phil Mujala | 2410218 
