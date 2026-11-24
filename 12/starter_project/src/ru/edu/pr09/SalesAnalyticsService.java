package ru.edu.pr09;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SalesAnalyticsService {
    public static final double VARIANT_6_THRESHOLD = 35.0;

    private static final Comparator<Sale> SALE_ORDER =
        Comparator.comparingDouble(Sale::amount).reversed()
            .thenComparing(Sale::id);

    public List<Sale> topSales(List<Sale> sales, double min, int limit) {
        if (sales == null) throw new IllegalArgumentException("Список продаж не задан");
        if (!Double.isFinite(min) || min < 0) throw new IllegalArgumentException("Некорректный минимум");
        if (limit < 0) throw new IllegalArgumentException("Лимит не может быть отрицательным");

        return sales.stream()
            .filter(sale -> sale.amount() >= min)
            .sorted(SALE_ORDER)
            .limit(limit)
            .toList();
    }

    public Optional<Sale> maxSale(List<Sale> sales) {
        if (sales == null) throw new IllegalArgumentException("Список продаж не задан");
        return sales.stream().max(Comparator.comparingDouble(Sale::amount));
    }

    public Map<String, Double> totalsByManager(List<Sale> sales) {
        if (sales == null) throw new IllegalArgumentException("Список продаж не задан");
        return sales.stream().collect(Collectors.groupingBy(
            Sale::manager, Collectors.summingDouble(Sale::amount)
        ));
    }

    public double averageByRegion(List<Sale> sales, String region) {
        if (sales == null) throw new IllegalArgumentException("Список продаж не задан");
        if (region == null || region.isBlank()) throw new IllegalArgumentException("Регион не задан");

        return sales.stream()
            .filter(sale -> sale.region().equalsIgnoreCase(region))
            .mapToDouble(Sale::amount)
            .average()
            .orElse(0.0);
    }

    public List<Sale> sortedSales(List<Sale> sales) {
        if (sales == null) throw new IllegalArgumentException("Список продаж не задан");
        return sales.stream().sorted(SALE_ORDER).toList();
    }

    public long countSalesAtLeast35(List<Sale> sales) {
        if (sales == null) throw new IllegalArgumentException("Список продаж не задан");
        return sales.stream()
            .filter(sale -> sale.amount() >= VARIANT_6_THRESHOLD)
            .count();
    }
}
