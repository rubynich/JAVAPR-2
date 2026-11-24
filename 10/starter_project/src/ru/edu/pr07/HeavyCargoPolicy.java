package ru.edu.pr07;

public class HeavyCargoPolicy implements DeliveryPolicy {
    private static final double MAX_DISTANCE_KM = 200.0;

    @Override
    public boolean supports(DeliveryRequest r) {
        return r != null
                && r.type() == DeliveryType.HEAVY_CARGO
                && r.distanceKm() <= MAX_DISTANCE_KM
                && r.weightKg() <= DeliveryLimits.MAX_WEIGHT_KG;
    }

    @Override
    public double calculateCost(DeliveryRequest r) {
        if (!supports(r)) {
            throw new IllegalArgumentException("Доставка груза невозможна: проверьте расстояние и вес");
        }
        return 300.0 + r.distanceKm() * 25.0;
    }
}
