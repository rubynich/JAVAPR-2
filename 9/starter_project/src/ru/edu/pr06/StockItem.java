package ru.edu.pr06;

import java.util.Objects;

/** Карточка одной позиции оборудования. Инвентарный код является ключом позиции. */
public class StockItem {
    private final String code;
    private final String name;
    private final Category category;
    private int quantity;

    public StockItem(String code, String name, Category category, int quantity) {
        this.code = requireText(code, "Инвентарный код");
        this.name = requireText(name, "Название");
        this.category = Objects.requireNonNull(category, "Категория не задана");
        if (quantity < 0) throw new IllegalArgumentException("Начальный остаток не может быть отрицательным");
        this.quantity = quantity;
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(field + " не должно быть пустым");
        return value.trim();
    }

    public void increase(int q) {
        if (q <= 0) throw new IllegalArgumentException("Количество прихода должно быть положительным");
        quantity = Math.addExact(quantity, q);
    }

    public void decrease(int q) {
        if (q <= 0) throw new IllegalArgumentException("Количество списания должно быть положительным");
        if (q > quantity) throw new IllegalArgumentException("Недостаточно оборудования на складе");
        quantity -= q;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public Category getCategory() { return category; }
    public int getQuantity() { return quantity; }

    /** Две карточки одной позиции равны, если совпадает уникальный инвентарный код. */
    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StockItem other)) return false;
        return code.equals(other.code);
    }
    @Override public int hashCode() { return Objects.hash(code); }

    @Override public String toString() {
        return code + " | " + name + " | " + category + " | остаток: " + quantity;
    }
}
