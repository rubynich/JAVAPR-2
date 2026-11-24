package ru.edu.pr07;

public class SelfCheck {
    private static int passed;
    private static int failed;

    public static void main(String[] args) {
        DeliveryService service = new DeliveryService(new DeliveryPolicyFactory());

        expectCost("STANDARD, обычный заказ",
                () -> service.calculate(new DeliveryRequest(DeliveryType.STANDARD, 12, 4.5, 12000)),
                340.0);
        expectCost("EXPRESS, обычный заказ",
                () -> service.calculate(new DeliveryRequest(DeliveryType.EXPRESS, 5, 1.2, 4000)),
                375.0);
        expectCost("PICKUP, самовывоз",
                () -> service.calculate(new DeliveryRequest(DeliveryType.PICKUP, 0, 10, 8000)),
                0.0);
        expectCost("HEAVY_CARGO, вес ровно 35 кг",
                () -> service.calculate(new DeliveryRequest(DeliveryType.HEAVY_CARGO, 20, 35, 15000)),
                800.0);
        expectCost("STANDARD, вес ровно 35 кг",
                () -> service.calculate(new DeliveryRequest(DeliveryType.STANDARD, 10, 35, 5000)),
                300.0);

        expectError("HEAVY_CARGO, вес 35.1 кг",
                () -> service.calculate(new DeliveryRequest(DeliveryType.HEAVY_CARGO, 20, 35.1, 15000)));
        expectError("HEAVY_CARGO, расстояние 201 км",
                () -> service.calculate(new DeliveryRequest(DeliveryType.HEAVY_CARGO, 201, 10, 15000)));
        expectError("нулевой вес",
                () -> { new DeliveryRequest(DeliveryType.STANDARD, 10, 0, 5000); return 0; });
        expectError("отрицательное расстояние",
                () -> { new DeliveryRequest(DeliveryType.STANDARD, -1, 5, 5000); return 0; });
        expectError("отсутствующий тип доставки",
                () -> { new DeliveryRequest(null, 10, 5, 5000); return 0; });
        expectError("нечисловое значение",
                () -> { new DeliveryRequest(DeliveryType.STANDARD, Double.NaN, 5, 5000); return 0; });

        System.out.println();
        System.out.println("ИТОГ: успешно " + passed + ", ошибок " + failed);
        if (failed > 0) {
            throw new AssertionError("SelfCheck завершился с ошибками");
        }
    }

    private static void expectCost(String name, Calculation action, double expected) {
        try {
            double actual = action.run();
            if (Math.abs(actual - expected) < 0.000001) {
                passed++;
                System.out.println("PASS: " + name + " -> " + actual);
            } else {
                failed++;
                System.out.println("FAIL: " + name + ", ожидалось " + expected + ", получено " + actual);
            }
        } catch (Exception e) {
            failed++;
            System.out.println("FAIL: " + name + " -> " + e.getMessage());
        }
    }

    private static void expectError(String name, Calculation action) {
        try {
            action.run();
            failed++;
            System.out.println("FAIL: " + name + " -> ожидалась ошибка");
        } catch (IllegalArgumentException e) {
            passed++;
            System.out.println("PASS: " + name + " -> " + e.getMessage());
        }
    }

    @FunctionalInterface
    private interface Calculation {
        double run();
    }
}
