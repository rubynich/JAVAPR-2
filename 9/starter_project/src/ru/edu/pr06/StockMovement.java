package ru.edu.pr06;

import java.util.Objects;

/** Неизменяемая запись о приходе или списании оборудования. */
public record StockMovement(String itemCode, MovementType type, int quantity) {
    public StockMovement {
        if (itemCode == null || itemCode.isBlank()) throw new IllegalArgumentException("Код позиции не задан");
        Objects.requireNonNull(type, "Тип операции не задан");
        if (quantity <= 0) throw new IllegalArgumentException("Количество операции должно быть положительным");
    }
}
