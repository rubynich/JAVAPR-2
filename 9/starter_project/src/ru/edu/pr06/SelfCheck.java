package ru.edu.pr06;

/** Автоматические проверки основных и граничных сценариев практической работы. */
public class SelfCheck {
    private static int passed = 0;

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        StockItem notebook = new StockItem("NB-01", "Notebook", Category.COMPUTER, 12);
        StockItem monitor = new StockItem("MN-01", "Monitor", Category.DISPLAY, 7);
        StockItem keyboard = new StockItem("KB-01", "Keyboard", Category.PERIPHERAL, 20);
        warehouse.addItem(notebook); warehouse.addItem(monitor); warehouse.addItem(keyboard);

        check("типовой приход", () -> warehouse.receive("NB-01", 3), () -> notebook.getQuantity() == 15);
        check("списание на верхней границе 35", () -> {
            warehouse.receive("KB-01", 20); warehouse.issue("KB-01", 35);
        }, () -> keyboard.getQuantity() == 5);
        check("списание сверх лимита 35 отклоняется", () -> expectError(() -> warehouse.issue("NB-01", 36)), () -> notebook.getQuantity() == 15);
        check("нулевое количество отклоняется", () -> expectError(() -> warehouse.receive("NB-01", 0)), () -> notebook.getQuantity() == 15);
        check("отрицательное количество отклоняется", () -> expectError(() -> warehouse.issue("NB-01", -1)), () -> notebook.getQuantity() == 15);
        check("недостаточный остаток отклоняется", () -> expectError(() -> warehouse.issue("MN-01", 8)), () -> monitor.getQuantity() == 7);
        check("повторный инвентарный код отклоняется", () -> expectError(() -> warehouse.addItem(new StockItem("NB-01", "Duplicate", Category.COMPUTER, 1))), () -> warehouse.getAllItems().size() == 3);
        check("несуществующий код отклоняется", () -> expectError(() -> warehouse.issue("XX-99", 1)), () -> warehouse.getMovements().size() == 3);
        check("пустой код отклоняется", () -> expectError(() -> warehouse.receive(" ", 1)), () -> true);
        check("поиск возвращает отдельный список", () -> warehouse.findByCategory(Category.COMPUTER).clear(), () -> warehouse.findByCategory(Category.COMPUTER).size() == 1);
        check("категории уникальны", () -> {}, () -> warehouse.getCategories().size() == 3);
        check("журнал фиксирует операции", () -> {}, () -> warehouse.getMovements().size() == 3);
        check("итоги по категориям", () -> {}, () -> warehouse.totalsByCategory().get(Category.COMPUTER) == 15 && warehouse.totalsByCategory().get(Category.DISPLAY) == 7 && warehouse.totalsByCategory().get(Category.PERIPHERAL) == 5);

        System.out.println("Итого: " + passed + " проверок пройдено.");
        if (passed != 13) throw new AssertionError("Не все проверки пройдены");
    }

    private static void check(String name, Runnable action, java.util.function.BooleanSupplier condition) {
        try { action.run(); if (!condition.getAsBoolean()) throw new AssertionError("условие не выполнено");
            passed++; System.out.println("PASS: " + name);
        } catch (Throwable e) { System.out.println("FAIL: " + name + " — " + e.getMessage()); throw e; }
    }
    private static void expectError(Runnable action) {
        try { action.run(); throw new AssertionError("ожидалось IllegalArgumentException"); }
        catch (IllegalArgumentException expected) { /* ожидаемая контролируемая ошибка */ }
    }
}
