package ru.edu.pr07;

public class PickupPolicy implements DeliveryPolicy {
    @Override
    public boolean supports(DeliveryRequest r) {
        return r != null
                && r.type() == DeliveryType.PICKUP
                && r.distanceKm() == 0.0
                && r.weightKg() <= DeliveryLimits.MAX_WEIGHT_KG;
    }

    @Override
    public double calculateCost(DeliveryRequest r) {
        if (!supports(r)) {
            throw new IllegalArgumentException("Самовывоз невозможен: расстояние должно быть 0 км, вес — не более 35 кг");
        }
        return 0.0;
    }
}
