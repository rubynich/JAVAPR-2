package ru.edu.pr07;

public class ExpressPolicy implements DeliveryPolicy {
    private static final double MAX_DISTANCE_KM = 30.0;

    @Override
    public boolean supports(DeliveryRequest r) {
        return r != null
                && r.type() == DeliveryType.EXPRESS
                && r.distanceKm() <= MAX_DISTANCE_KM
                && r.weightKg() <= DeliveryLimits.MAX_WEIGHT_KG;
    }

    @Override
    public double calculateCost(DeliveryRequest r) {
        if (!supports(r)) {
            throw new IllegalArgumentException("Экспресс-доставка невозможна: проверьте расстояние и вес");
        }
        return 200.0 + r.distanceKm() * 35.0;
    }
}
