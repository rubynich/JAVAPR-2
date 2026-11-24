package ru.edu.pr09;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SelfCheck {
    private static int passed;
    private static int failed;

    public static void main(String[] args) {
        SalesAnalyticsService service = new SalesAnalyticsService();
        List<Sale> sales = new ArrayList<>(List.of(
            new Sale("S1", "Ivanov", "Moscow", 150000),
            new Sale("S2", "Petrova", "SPb", 92000),
            new Sale("S3", "Ivanov", "Moscow", 240000),
            new Sale("S4", "Lee", "Kazan", 45000)
        ));

        check("topSales отбирает и сортирует", service.topSales(sales, 100000, 2)
            .equals(List.of(sales.get(2), sales.get(0))));
        check("topSales ограничивает количество", service.topSales(sales, 0, 1).size() == 1);
        check("maxSale возвращает Optional с максимумом",
            service.maxSale(sales).equals(Optional.of(sales.get(2))));
        check("maxSale для пустого списка возвращает Optional.empty",
            service.maxSale(List.of()).isEmpty());

        Map<String, Double> totals = service.totalsByManager(sales);
        check("сумма продаж Ivanov равна 390000",
            Math.abs(totals.get("Ivanov") - 390000.0) < 0.001);
        check("группировка создаёт Map для трёх менеджеров", totals.size() == 3);

        check("среднее по Moscow равно 195000",
            Math.abs(service.averageByRegion(sales, "Moscow") - 195000.0) < 0.001);
        check("для неизвестного региона возвращается 0",
            service.averageByRegion(sales, "Unknown") == 0.0);

        check("Comparator сортирует по сумме и затем по id",
            service.sortedSales(List.of(
                new Sale("B", "M", "R", 50),
                new Sale("A", "M", "R", 50),
                new Sale("C", "M", "R", 70)
            )).stream().map(Sale::id).toList().equals(List.of("C", "A", "B")));

        check("исходный список не изменяется", sales.get(0).id().equals("S1")
            && sales.size() == 4);

        check("вариант 6 считает продажи от 35 включительно",
            service.countSalesAtLeast35(List.of(
                new Sale("A", "M", "R", 34.99),
                new Sale("B", "M", "R", 35.0),
                new Sale("C", "M", "R", 40.0)
            )) == 2);

        System.out.println();
        System.out.println("ИТОГО: " + passed + " пройдено, " + failed + " ошибок");
        if (failed > 0) System.exit(1);
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS " + name);
        } else {
            failed++;
            System.out.println("FAIL " + name);
        }
    }
}
