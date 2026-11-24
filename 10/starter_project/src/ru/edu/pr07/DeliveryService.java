package ru.edu.pr07;

public class DeliveryService {
    private final DeliveryPolicyFactory factory;

    public DeliveryService(DeliveryPolicyFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("Фабрика не задана");
        }
        this.factory = factory;
    }

    public double calculate(DeliveryRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Заказ не задан");
        }
        DeliveryPolicy policy = factory.get(request.type());
        if (!policy.supports(request)) {
            throw new IllegalArgumentException("Доставка невозможна: превышено ограничение или неверные параметры");
        }
        return policy.calculateCost(request);
    }
}
