package ru.edu.pr07;

public class StandardCourierPolicy implements DeliveryPolicy {
    private static final double MAX_DISTANCE_KM = 100.0;

    @Override
    public boolean supports(DeliveryRequest r) {
        return r != null
                && r.type() == DeliveryType.STANDARD
                && r.distanceKm() <= MAX_DISTANCE_KM
                && r.weightKg() <= DeliveryLimits.MAX_WEIGHT_KG;
    }

    @Override
    public double calculateCost(DeliveryRequest r) {
        requireSupported(r);
        return 100.0 + r.distanceKm() * 20.0;
    }

    private void requireSupported(DeliveryRequest r) {
        if (!supports(r)) {
            throw new IllegalArgumentException("Стандартная доставка невозможна: проверьте расстояние и вес");
        }
    }
}
