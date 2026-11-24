package ru.edu.pr09;

public record Sale(String id, String manager, String region, double amount) {
    public Sale {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Пустой id");
        if (manager == null || manager.isBlank()) throw new IllegalArgumentException("Пустой менеджер");
        if (region == null || region.isBlank()) throw new IllegalArgumentException("Пустой регион");
        if (!Double.isFinite(amount) || amount < 0) throw new IllegalArgumentException("Некорректная сумма");
    }
}
