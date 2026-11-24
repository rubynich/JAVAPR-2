package ru.edu.pr07;

public record DeliveryRequest(
        DeliveryType type,
        double distanceKm,
        double weightKg,
        double orderAmount
) {
    public DeliveryRequest {
        if (type == null) {
            throw new IllegalArgumentException("Тип доставки не указан");
        }
        if (!Double.isFinite(distanceKm)
                || !Double.isFinite(weightKg)
                || !Double.isFinite(orderAmount)) {
            throw new IllegalArgumentException("Числовые параметры должны быть конечными");
        }
        if (distanceKm < 0 || weightKg <= 0 || orderAmount < 0) {
            throw new IllegalArgumentException("Расстояние и сумма не могут быть отрицательными, вес должен быть положительным");
        }
    }
}
